package com.rohit.clinic.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohit.clinic.dto.request.CreateIncomingLeadFromWhatsAppRequest;
import com.rohit.clinic.dto.request.FindMatchesRequest;
import com.rohit.clinic.dto.request.ProcessWhatsAppMessageRequest;
import com.rohit.clinic.dto.request.SaveWhatsAppIncomingMessageRequest;
import com.rohit.clinic.dto.response.IncomingLeadResponse;
import com.rohit.clinic.dto.response.MatchResultResponse;
import com.rohit.clinic.dto.response.ProcessWhatsAppMessageResponse;
import com.rohit.clinic.dto.response.SubscriptionAccessResult;
import com.rohit.clinic.dto.response.WhatsAppIncomingMessageResponse;
import com.rohit.clinic.entity.TenantMessageSource;
import com.rohit.clinic.exception.BadRequestException;
import com.rohit.clinic.exception.SubscriptionAccessDeniedException;
import com.rohit.clinic.repository.TenantMessageSourceRepository;
import com.rohit.clinic.service.InboundLeadProcessingService;
import com.rohit.clinic.service.IncomingLeadService;
import com.rohit.clinic.service.MatchFindingService;
import com.rohit.clinic.service.MatchReplyBuilderService;
import com.rohit.clinic.service.SubscriptionAccessService;
import com.rohit.clinic.service.WhatsAppIncomingMessageService;
import com.rohit.clinic.service.WhatsAppOutboundMessageService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InboundLeadProcessingServiceImpl implements InboundLeadProcessingService {

    private static final Logger log = LoggerFactory.getLogger(InboundLeadProcessingServiceImpl.class);
    private static final int DEFAULT_MATCH_LIMIT = 5;

    private final WhatsAppIncomingMessageService whatsAppIncomingMessageService;
    private final IncomingLeadService incomingLeadService;
    private final MatchFindingService matchFindingService;
    private final MatchReplyBuilderService matchReplyBuilderService;
    private final SubscriptionAccessService subscriptionAccessService;
    private final WhatsAppOutboundMessageService whatsAppOutboundMessageService;
    private final ObjectMapper objectMapper;
    private final TenantMessageSourceRepository tenantMessageSourceRepository;

    public InboundLeadProcessingServiceImpl(
            WhatsAppIncomingMessageService whatsAppIncomingMessageService,
            IncomingLeadService incomingLeadService,
            MatchFindingService matchFindingService,
            MatchReplyBuilderService matchReplyBuilderService,
            SubscriptionAccessService subscriptionAccessService,
            WhatsAppOutboundMessageService whatsAppOutboundMessageService,
            ObjectMapper objectMapper,
            TenantMessageSourceRepository tenantMessageSourceRepository
    ) {
        this.whatsAppIncomingMessageService = whatsAppIncomingMessageService;
        this.incomingLeadService = incomingLeadService;
        this.matchFindingService = matchFindingService;
        this.matchReplyBuilderService = matchReplyBuilderService;
        this.subscriptionAccessService = subscriptionAccessService;
        this.whatsAppOutboundMessageService = whatsAppOutboundMessageService;
        this.objectMapper = objectMapper;
        this.tenantMessageSourceRepository = tenantMessageSourceRepository;
    }

    @Override
    @Transactional
    public ProcessWhatsAppMessageResponse processIncomingWhatsAppMessage(ProcessWhatsAppMessageRequest request) {
        validateRequest(request);

        WhatsAppIncomingMessageResponse savedMessage =
                whatsAppIncomingMessageService.saveIncomingMessage(buildSaveRequest(request));
        boolean alreadyProcessedMessage = Boolean.TRUE.equals(savedMessage.getProcessedFlag());

        SubscriptionAccessResult access = subscriptionAccessService.checkLeadTriggerAccess(request.getTenantId());
        if (!alreadyProcessedMessage && !access.isAllowed()) {
            throw new SubscriptionAccessDeniedException(access.getReason());
        }

        IncomingLeadResponse lead =
                incomingLeadService.createFromWhatsApp(buildCreateLeadRequest(savedMessage.getId()));

        List<MatchResultResponse> matches =
                matchFindingService.findTopMatches(buildFindMatchesRequest(lead.getId(), resolveMatchLimit(access)));

        ProcessWhatsAppMessageResponse response = new ProcessWhatsAppMessageResponse();
        response.setLeadId(lead.getId());
        response.setMatchCount(matches.size());
        response.setReplyMessage(matchReplyBuilderService.buildReplyMessage(matches));

        if (!alreadyProcessedMessage) {
            subscriptionAccessService.recordLeadTriggered(access.getSubscriptionId());
        }
        return response;
    }

    @Override
    @Transactional
    public void handleIncomingWhatsAppWebhook(String payload) {
        if (payload == null || payload.isBlank()) {
            throw new BadRequestException("WhatsApp webhook payload is required");
        }

        JsonNode root = readWebhookPayload(payload);
        for (JsonNode entry : root.path("entry")) {
            for (JsonNode change : entry.path("changes")) {
                JsonNode value = change.path("value");
                JsonNode messages = value.path("messages");
                if (!messages.isArray() || messages.size() == 0) {
                    continue;
                }

                TenantMessageSource source = resolveMessageSource(value.path("metadata"));
                for (JsonNode message : messages) {
                    if (!isTextMessage(message)) {
                        log.info("[LEAD][WHATSAPP] skipped non-text webhook message id={} type={}",
                                textOrNull(message.path("id")), textOrNull(message.path("type")));
                        continue;
                    }
                    ProcessWhatsAppMessageRequest request = buildWebhookProcessRequest(source, value, message);
                    ProcessWhatsAppMessageResponse response = processIncomingWhatsAppMessage(request);
                    whatsAppOutboundMessageService.sendTextMessage(
                            request.getSenderPhone(),
                            response.getReplyMessage()
                    );
                }
            }
        }
    }

    private Integer resolveMatchLimit(SubscriptionAccessResult access) {
        if (access == null || !access.isAllowed()) {
            return DEFAULT_MATCH_LIMIT;
        }
        return access.getMatchLimit();
    }

    private void validateRequest(ProcessWhatsAppMessageRequest request) {
        if (request == null) {
            throw new BadRequestException("WhatsApp processing request is required");
        }
        if (request.getTenantId() == null) {
            throw new BadRequestException("tenantId is required");
        }
        if (request.getMessageText() == null || request.getMessageText().isBlank()) {
            throw new BadRequestException("messageText is required");
        }
    }

    private SaveWhatsAppIncomingMessageRequest buildSaveRequest(ProcessWhatsAppMessageRequest request) {
        SaveWhatsAppIncomingMessageRequest saveRequest = new SaveWhatsAppIncomingMessageRequest();
        saveRequest.setTenantId(request.getTenantId());
        saveRequest.setMessageSourceId(request.getMessageSourceId());
        saveRequest.setWhatsappMessageId(request.getMessageKey());
        saveRequest.setSenderPhone(request.getSenderPhone());
        saveRequest.setSenderName(request.getSenderName());
        saveRequest.setMessageText(request.getMessageText());
        saveRequest.setReceivedAt(request.getReceivedAt());
        return saveRequest;
    }

    private CreateIncomingLeadFromWhatsAppRequest buildCreateLeadRequest(Long messageId) {
        CreateIncomingLeadFromWhatsAppRequest request = new CreateIncomingLeadFromWhatsAppRequest();
        request.setWhatsappMessageId(messageId);
        return request;
    }

    private FindMatchesRequest buildFindMatchesRequest(Long leadId, Integer matchLimit) {
        FindMatchesRequest request = new FindMatchesRequest();
        request.setIncomingLeadId(leadId);
        request.setLimit(matchLimit != null ? matchLimit : DEFAULT_MATCH_LIMIT);
        return request;
    }

    private JsonNode readWebhookPayload(String payload) {
        try {
            return objectMapper.readTree(payload);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("Invalid WhatsApp webhook payload");
        }
    }

    private TenantMessageSource resolveMessageSource(JsonNode metadata) {
        String displayPhoneNumber = textOrNull(metadata.path("display_phone_number"));
        String phoneNumberId = textOrNull(metadata.path("phone_number_id"));
        return findMessageSource(displayPhoneNumber)
                .or(() -> findMessageSource(phoneNumberId))
                .orElseThrow(() -> new BadRequestException("No tenant message source found for WhatsApp webhook"));
    }

    private Optional<TenantMessageSource> findMessageSource(String phoneNumber) {
        String normalizedPhoneNumber = normalizePhone(phoneNumber);
        if (normalizedPhoneNumber == null) {
            return Optional.empty();
        }

        return tenantMessageSourceRepository.findAll()
                .stream()
                .filter(source -> normalizedPhoneNumber.equals(normalizePhone(source.getPhoneNumber())))
                .findFirst();
    }

    private ProcessWhatsAppMessageRequest buildWebhookProcessRequest(
            TenantMessageSource source,
            JsonNode value,
            JsonNode message
    ) {
        if (source.getTenant() == null || source.getTenant().getId() == null) {
            throw new BadRequestException("WhatsApp message source is not linked to a tenant");
        }

        String senderPhone = textOrNull(message.path("from"));
        ProcessWhatsAppMessageRequest request = new ProcessWhatsAppMessageRequest();
        request.setTenantId(source.getTenant().getId());
        request.setMessageSourceId(source.getId());
        request.setSenderPhone(senderPhone);
        request.setSenderName(resolveSenderName(value, senderPhone));
        request.setMessageText(textOrNull(message.path("text").path("body")));
        request.setMessageKey(textOrNull(message.path("id")));
        request.setReceivedAt(resolveReceivedAt(message.path("timestamp")));
        return request;
    }

    private boolean isTextMessage(JsonNode message) {
        return "text".equals(textOrNull(message.path("type")))
                && textOrNull(message.path("text").path("body")) != null;
    }

    private String resolveSenderName(JsonNode value, String senderPhone) {
        JsonNode contacts = value.path("contacts");
        if (!contacts.isArray() || contacts.size() == 0) {
            return null;
        }

        String normalizedSenderPhone = normalizePhone(senderPhone);
        for (JsonNode contact : contacts) {
            if (normalizedSenderPhone != null
                    && normalizedSenderPhone.equals(normalizePhone(textOrNull(contact.path("wa_id"))))) {
                return textOrNull(contact.path("profile").path("name"));
            }
        }
        return textOrNull(contacts.get(0).path("profile").path("name"));
    }

    private LocalDateTime resolveReceivedAt(JsonNode timestamp) {
        String timestampValue = textOrNull(timestamp);
        if (timestampValue == null) {
            return LocalDateTime.now();
        }

        try {
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(timestampValue)), ZoneOffset.UTC);
        } catch (NumberFormatException ex) {
            return LocalDateTime.now();
        }
    }

    private String textOrNull(JsonNode node) {
        if (node == null || node.isMissingNode() || node.isNull()) {
            return null;
        }
        String value = node.asText();
        return value != null && !value.isBlank() ? value : null;
    }

    private String normalizePhone(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String normalized = value.replaceAll("\\D", "");
        return !normalized.isBlank() ? normalized : null;
    }
}
