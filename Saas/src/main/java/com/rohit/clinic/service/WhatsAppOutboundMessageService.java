package com.rohit.clinic.service;

public interface WhatsAppOutboundMessageService {

    void sendTextMessage(String toPhone, String messageBody);
}
