package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.request.MarkWhatsAppMessageFailedRequest;
import com.rohit.clinic.dto.request.MarkWhatsAppMessageParsedRequest;
import com.rohit.clinic.dto.request.SaveWhatsAppIncomingMessageRequest;
import com.rohit.clinic.dto.response.WhatsAppIncomingMessageResponse;
import com.rohit.clinic.entity.ParseStatus;
import com.rohit.clinic.entity.Tenant;
import com.rohit.clinic.entity.TenantMessageSource;
import com.rohit.clinic.entity.WhatsAppIncomingMessage;
import com.rohit.clinic.exception.BadRequestException;
import com.rohit.clinic.exception.NotFoundException;
import com.rohit.clinic.repository.TenantMessageSourceRepository;
import com.rohit.clinic.repository.TenantRepository;
import com.rohit.clinic.repository.WhatsAppIncomingMessageRepository;
import com.rohit.clinic.service.WhatsAppIncomingMessageService;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppIncomingMessageServiceImpl implements WhatsAppIncomingMessageService {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppIncomingMessageServiceImpl.class);

    private final WhatsAppIncomingMessageRepository repository;
    private final TenantRepository tenantRepository;
    private final TenantMessageSourceRepository tenantMessageSourceRepository;

    public WhatsAppIncomingMessageServiceImpl(
            WhatsAppIncomingMessageRepository repository,
            TenantRepository tenantRepository,
            TenantMessageSourceRepository tenantMessageSourceRepository
    ) {
        this.repository = repository;
        this.tenantRepository = tenantRepository;
        this.tenantMessageSourceRepository = tenantMessageSourceRepository;
    }

    @Override
    public WhatsAppIncomingMessageResponse saveIncomingMessage(SaveWhatsAppIncomingMessageRequest request) {
        validateRequest(request);

        Tenant tenant = tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
        TenantMessageSource source = resolveMessageSource(request.getMessageSourceId(), tenant.getId());

        String whatsappMessageId = request.getWhatsappMessageId();
        if (whatsappMessageId != null && !whatsappMessageId.isBlank()) {
            WhatsAppIncomingMessage existing = repository.findByTenant_IdAndWhatsappMessageId(tenant.getId(), whatsappMessageId)
                    .orElse(null);
            if (existing != null) {
                log.info("[LEAD][WHATSAPP] duplicate message reused id={} tenantId={} messageKey={}",
                        existing.getId(), tenant.getId(), whatsappMessageId);
                return toResponse(existing);
            }
        }

        WhatsAppIncomingMessage message = new WhatsAppIncomingMessage();
        message.setTenant(tenant);
        message.setMessageSource(source);
        message.setWhatsappMessageId(whatsappMessageId);
        message.setSenderPhone(request.getSenderPhone());
        message.setSenderName(request.getSenderName());
        message.setMessageText(request.getMessageText());
        message.setReceivedAt(request.getReceivedAt() != null ? request.getReceivedAt() : LocalDateTime.now());
        message.setProcessedFlag(false);
        message.setParseStatus(ParseStatus.NOT_PROCESSED);
        WhatsAppIncomingMessage saved = repository.save(message);
        log.info("[LEAD][WHATSAPP] saved raw message id={} tenantId={} messageKey={}",
                saved.getId(), tenant.getId(), whatsappMessageId);
        return toResponse(saved);
    }

    @Override
    public WhatsAppIncomingMessageResponse markParsed(MarkWhatsAppMessageParsedRequest request) {
        WhatsAppIncomingMessage message = repository.findById(request.getMessageId())
                .orElseThrow(() -> new NotFoundException("WhatsApp message not found"));

        message.setProcessedFlag(true);
        message.setParseStatus(resolveParsedStatus(request.getParseConfidence()));
        message.setParseConfidence(request.getParseConfidence());
        return toResponse(repository.save(message));
    }

    @Override
    public WhatsAppIncomingMessageResponse markFailed(MarkWhatsAppMessageFailedRequest request) {
        WhatsAppIncomingMessage message = repository.findById(request.getMessageId())
                .orElseThrow(() -> new NotFoundException("WhatsApp message not found"));

        message.setProcessedFlag(false);
        message.setParseStatus(ParseStatus.FAILED);
        message.setRemarks(request.getRemarks());
        return toResponse(repository.save(message));
    }

    @Override
    public List<WhatsAppIncomingMessageResponse> getUnprocessedMessages() {
        return repository.findByProcessedFlagFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ParseStatus resolveParsedStatus(Integer parseConfidence) {
        if (parseConfidence != null && parseConfidence >= 80) {
            return ParseStatus.PARSED_HIGH_CONFIDENCE;
        }
        return ParseStatus.PARSED_LOW_CONFIDENCE;
    }

    private void validateRequest(SaveWhatsAppIncomingMessageRequest request) {
        if (request == null) {
            throw new BadRequestException("WhatsApp message request is required");
        }
        if (request.getTenantId() == null) {
            throw new BadRequestException("tenantId is required");
        }
        if (request.getMessageText() == null || request.getMessageText().isBlank()) {
            throw new BadRequestException("messageText is required");
        }
    }

    private TenantMessageSource resolveMessageSource(Long messageSourceId, Long tenantId) {
        if (messageSourceId == null) {
            return null;
        }

        TenantMessageSource source = tenantMessageSourceRepository.findById(messageSourceId)
                .orElseThrow(() -> new NotFoundException("Message source not found"));
        Long sourceTenantId = source.getTenant() != null ? source.getTenant().getId() : null;
        if (!tenantId.equals(sourceTenantId)) {
            throw new BadRequestException("Message source does not belong to tenant");
        }
        return source;
    }

    private WhatsAppIncomingMessageResponse toResponse(WhatsAppIncomingMessage message) {
        WhatsAppIncomingMessageResponse response = new WhatsAppIncomingMessageResponse();
        response.setId(message.getId());
        response.setTenantId(message.getTenant() != null ? message.getTenant().getId() : null);
        response.setMessageSourceId(message.getMessageSource() != null ? message.getMessageSource().getId() : null);
        response.setWhatsappMessageId(message.getWhatsappMessageId());
        response.setSenderPhone(message.getSenderPhone());
        response.setSenderName(message.getSenderName());
        response.setMessageText(message.getMessageText());
        response.setReceivedAt(message.getReceivedAt());
        response.setProcessedFlag(message.getProcessedFlag());
        response.setParseStatus(message.getParseStatus());
        response.setParseConfidence(message.getParseConfidence());
        response.setExtractedSide(message.getExtractedSide());
        response.setExtractedProductText(message.getExtractedProductText());
        response.setExtractedQuantity(message.getExtractedQuantity());
        response.setExtractedUnit(message.getExtractedUnit());
        response.setExtractedLocationFrom(message.getExtractedLocationFrom());
        response.setExtractedLocationTo(message.getExtractedLocationTo());
        response.setRemarks(message.getRemarks());
        return response;
    }
}
