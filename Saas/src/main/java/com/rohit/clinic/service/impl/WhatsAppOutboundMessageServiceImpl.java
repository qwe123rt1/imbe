package com.rohit.clinic.service.impl;

import com.rohit.clinic.service.WhatsAppOutboundMessageService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsAppOutboundMessageServiceImpl implements WhatsAppOutboundMessageService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${whatsapp.meta.phone-number-id}")
    private String phoneNumberId;

    @Value("${whatsapp.meta.access-token}")
    private String accessToken;

    @Override
    public void sendTextMessage(String toPhone, String messageBody) {
        if (toPhone == null || toPhone.isBlank()) {
            return;
        }
        if (messageBody == null || messageBody.isBlank()) {
            return;
        }

        String url = "https://graph.facebook.com/v19.0/" + phoneNumberId + "/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> text = new HashMap<>();
        text.put("body", messageBody);

        Map<String, Object> payload = new HashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("to", normalizePhone(toPhone));
        payload.put("type", "text");
        payload.put("text", text);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("Failed to send WhatsApp message. Response: " + response.getBody());
        }
    }

    private String normalizePhone(String value) {
        return value == null ? null : value.replaceAll("\\D", "");
    }
}
