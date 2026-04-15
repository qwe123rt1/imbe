package com.rohit.clinic.controller;

import com.rohit.clinic.dto.request.DetectedMessageRequest;
import com.rohit.clinic.dto.response.ProcessWhatsAppMessageResponse;
import com.rohit.clinic.service.DetectedMessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/detected-messages")
public class DetectedMessageController {

    private final DetectedMessageService detectedMessageService;

    public DetectedMessageController(DetectedMessageService detectedMessageService) {
        this.detectedMessageService = detectedMessageService;
    }

    @PostMapping("/process")
    public ProcessWhatsAppMessageResponse process(@RequestBody DetectedMessageRequest request) {
        return detectedMessageService.processDetectedMessage(request);
    }
}
