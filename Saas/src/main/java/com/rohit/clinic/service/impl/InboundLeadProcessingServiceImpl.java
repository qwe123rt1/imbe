package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.request.CreateIncomingLeadFromWhatsAppRequest;
import com.rohit.clinic.dto.request.FindMatchesRequest;
import com.rohit.clinic.dto.request.ProcessWhatsAppMessageRequest;
import com.rohit.clinic.dto.request.SaveWhatsAppIncomingMessageRequest;
import com.rohit.clinic.dto.response.IncomingLeadResponse;
import com.rohit.clinic.dto.response.MatchResultResponse;
import com.rohit.clinic.dto.response.ProcessWhatsAppMessageResponse;
import com.rohit.clinic.dto.response.SubscriptionAccessResult;
import com.rohit.clinic.dto.response.WhatsAppIncomingMessageResponse;
import com.rohit.clinic.exception.SubscriptionAccessDeniedException;
import com.rohit.clinic.service.InboundLeadProcessingService;
import com.rohit.clinic.service.IncomingLeadService;
import com.rohit.clinic.service.MatchFindingService;
import com.rohit.clinic.service.MatchReplyBuilderService;
import com.rohit.clinic.service.SubscriptionAccessService;
import com.rohit.clinic.service.WhatsAppIncomingMessageService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InboundLeadProcessingServiceImpl implements InboundLeadProcessingService {

    private static final int DEFAULT_MATCH_LIMIT = 5;

    private final WhatsAppIncomingMessageService whatsAppIncomingMessageService;
    private final IncomingLeadService incomingLeadService;
    private final MatchFindingService matchFindingService;
    private final MatchReplyBuilderService matchReplyBuilderService;
    private final SubscriptionAccessService subscriptionAccessService;

    public InboundLeadProcessingServiceImpl(
            WhatsAppIncomingMessageService whatsAppIncomingMessageService,
            IncomingLeadService incomingLeadService,
            MatchFindingService matchFindingService,
            MatchReplyBuilderService matchReplyBuilderService,
            SubscriptionAccessService subscriptionAccessService
    ) {
        this.whatsAppIncomingMessageService = whatsAppIncomingMessageService;
        this.incomingLeadService = incomingLeadService;
        this.matchFindingService = matchFindingService;
        this.matchReplyBuilderService = matchReplyBuilderService;
        this.subscriptionAccessService = subscriptionAccessService;
    }

    @Override
    @Transactional
    public ProcessWhatsAppMessageResponse processIncomingWhatsAppMessage(ProcessWhatsAppMessageRequest request) {
        SubscriptionAccessResult access = subscriptionAccessService.checkLeadTriggerAccess(
                request != null ? request.getTenantId() : null
        );
        if (!access.isAllowed()) {
            throw new SubscriptionAccessDeniedException(access.getReason());
        }

        WhatsAppIncomingMessageResponse savedMessage =
                whatsAppIncomingMessageService.saveIncomingMessage(buildSaveRequest(request));

        IncomingLeadResponse lead =
                incomingLeadService.createFromWhatsApp(buildCreateLeadRequest(savedMessage.getId()));

        List<MatchResultResponse> matches =
                matchFindingService.findTopMatches(buildFindMatchesRequest(lead.getId(), access.getMatchLimit()));

        ProcessWhatsAppMessageResponse response = new ProcessWhatsAppMessageResponse();
        response.setLeadId(lead.getId());
        response.setMatchCount(matches.size());
        response.setReplyMessage(matchReplyBuilderService.buildReplyMessage(matches));

        subscriptionAccessService.recordLeadTriggered(access.getSubscriptionId());
        return response;
    }

    private SaveWhatsAppIncomingMessageRequest buildSaveRequest(ProcessWhatsAppMessageRequest request) {
        SaveWhatsAppIncomingMessageRequest saveRequest = new SaveWhatsAppIncomingMessageRequest();
        if (request == null) {
            return saveRequest;
        }

        saveRequest.setWhatsappMessageId(request.getMessageKey());
        saveRequest.setSenderPhone(request.getSenderPhone());
        saveRequest.setSenderName(request.getSenderName());
        saveRequest.setMessageText(request.getMessageText());
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
}
