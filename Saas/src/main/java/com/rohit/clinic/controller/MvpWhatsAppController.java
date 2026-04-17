package com.rohit.clinic.controller;

import com.rohit.clinic.dto.request.ProcessWhatsAppMessageRequest;
import com.rohit.clinic.dto.request.SendWhatsAppTextMessageRequest;
import com.rohit.clinic.dto.response.ProcessWhatsAppMessageResponse;
import com.rohit.clinic.exception.BadRequestException;
import com.rohit.clinic.service.InboundLeadProcessingService;
import com.rohit.clinic.service.WhatsAppOutboundMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/whatsapp", "/api/mvp/whatsapp"})
public class MvpWhatsAppController {

    @Value("${whatsapp.meta.verify-token}")
    private String verifyToken;

    private final InboundLeadProcessingService inboundLeadProcessingService;
    private final WhatsAppOutboundMessageService whatsAppOutboundMessageService;

    public MvpWhatsAppController(
            InboundLeadProcessingService inboundLeadProcessingService,
            WhatsAppOutboundMessageService whatsAppOutboundMessageService
    ) {
        this.inboundLeadProcessingService = inboundLeadProcessingService;
        this.whatsAppOutboundMessageService = whatsAppOutboundMessageService;
    }

    @GetMapping("/webhook")
    public ResponseEntity<String> verifyWebhook(
            @RequestParam(name = "hub.mode", required = false) String mode,
            @RequestParam(name = "hub.verify_token", required = false) String token,
            @RequestParam(name = "hub.challenge", required = false) String challenge
    ) {
        if ("subscribe".equals(mode) && verifyToken != null && verifyToken.equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid verify token");
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveWebhook(@RequestBody String payload) {
        inboundLeadProcessingService.handleIncomingWhatsAppWebhook(payload);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/process")
    public ProcessWhatsAppMessageResponse process(@RequestBody ProcessWhatsAppMessageRequest request) {
        return inboundLeadProcessingService.processIncomingWhatsAppMessage(request);
    }

    @PostMapping("/send-test")
    public ResponseEntity<String> sendTestMessage(@RequestBody SendWhatsAppTextMessageRequest request) {
        validateSendMessageRequest(request);
        whatsAppOutboundMessageService.sendTextMessage(request.getToPhone(), request.getMessageBody());
        return ResponseEntity.ok("Message sent");
    }

    private void validateSendMessageRequest(SendWhatsAppTextMessageRequest request) {
        if (request == null) {
            throw new BadRequestException("WhatsApp send request is required");
        }
        if (request.getToPhone() == null || request.getToPhone().isBlank()) {
            throw new BadRequestException("toPhone is required");
        }
        if (request.getMessageBody() == null || request.getMessageBody().isBlank()) {
            throw new BadRequestException("messageBody is required");
        }
    }
}
