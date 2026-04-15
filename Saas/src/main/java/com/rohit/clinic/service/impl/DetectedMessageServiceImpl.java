package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.request.DetectedMessageRequest;
import com.rohit.clinic.dto.request.ProcessWhatsAppMessageRequest;
import com.rohit.clinic.dto.response.ProcessWhatsAppMessageResponse;
import com.rohit.clinic.entity.PermissionStatus;
import com.rohit.clinic.entity.TenantMessageSource;
import com.rohit.clinic.exception.NotFoundException;
import com.rohit.clinic.exception.SubscriptionAccessDeniedException;
import com.rohit.clinic.repository.TenantMessageSourceRepository;
import com.rohit.clinic.service.DetectedMessageService;
import com.rohit.clinic.service.InboundLeadProcessingService;
import org.springframework.stereotype.Service;

@Service
public class DetectedMessageServiceImpl implements DetectedMessageService {

    private final TenantMessageSourceRepository tenantMessageSourceRepository;
    private final InboundLeadProcessingService inboundLeadProcessingService;

    public DetectedMessageServiceImpl(
            TenantMessageSourceRepository tenantMessageSourceRepository,
            InboundLeadProcessingService inboundLeadProcessingService
    ) {
        this.tenantMessageSourceRepository = tenantMessageSourceRepository;
        this.inboundLeadProcessingService = inboundLeadProcessingService;
    }

    @Override
    public ProcessWhatsAppMessageResponse processDetectedMessage(DetectedMessageRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Detected message request is required");
        }

        TenantMessageSource source = tenantMessageSourceRepository.findById(request.getMessageSourceId())
                .orElseThrow(() -> new NotFoundException("Message source not found"));

        validateTenant(request, source);
        validatePermission(source);

        return inboundLeadProcessingService.processIncomingWhatsAppMessage(buildProcessRequest(request));
    }

    private void validateTenant(DetectedMessageRequest request, TenantMessageSource source) {
        Long sourceTenantId = source.getTenant() != null ? source.getTenant().getId() : null;
        if (request.getTenantId() == null || !request.getTenantId().equals(sourceTenantId)) {
            throw new SubscriptionAccessDeniedException("Message source does not belong to tenant");
        }
    }

    private void validatePermission(TenantMessageSource source) {
        if (source.getPermissionStatus() != PermissionStatus.GRANTED) {
            throw new SubscriptionAccessDeniedException("Message source permission is not granted");
        }
    }

    private ProcessWhatsAppMessageRequest buildProcessRequest(DetectedMessageRequest request) {
        ProcessWhatsAppMessageRequest processRequest = new ProcessWhatsAppMessageRequest();
        processRequest.setTenantId(request.getTenantId());
        processRequest.setSenderName(request.getSenderName());
        processRequest.setSenderPhone(request.getSenderPhone());
        processRequest.setMessageText(request.getMessageText());
        processRequest.setMessageKey(request.getMessageKey());
        return processRequest;
    }
}
