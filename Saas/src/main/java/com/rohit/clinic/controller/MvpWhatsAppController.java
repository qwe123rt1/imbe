package com.rohit.clinic.controller;

import com.rohit.clinic.dto.request.ProcessWhatsAppMessageRequest;
import com.rohit.clinic.dto.response.ProcessWhatsAppMessageResponse;
import com.rohit.clinic.service.InboundLeadProcessingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mvp/whatsapp")
public class MvpWhatsAppController {

    private final InboundLeadProcessingService inboundLeadProcessingService;

    public MvpWhatsAppController(InboundLeadProcessingService inboundLeadProcessingService) {
        this.inboundLeadProcessingService = inboundLeadProcessingService;
    }

    @PostMapping("/process")
    public ProcessWhatsAppMessageResponse process(@RequestBody ProcessWhatsAppMessageRequest request) {
        return inboundLeadProcessingService.processIncomingWhatsAppMessage(request);
    }
}
