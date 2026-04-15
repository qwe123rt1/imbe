package com.rohit.clinic.service;

import com.rohit.clinic.dto.request.MarkWhatsAppMessageFailedRequest;
import com.rohit.clinic.dto.request.MarkWhatsAppMessageParsedRequest;
import com.rohit.clinic.dto.request.SaveWhatsAppIncomingMessageRequest;
import com.rohit.clinic.dto.response.WhatsAppIncomingMessageResponse;
import java.util.List;

public interface WhatsAppIncomingMessageService {

    WhatsAppIncomingMessageResponse saveIncomingMessage(SaveWhatsAppIncomingMessageRequest request);

    WhatsAppIncomingMessageResponse markParsed(MarkWhatsAppMessageParsedRequest request);

    WhatsAppIncomingMessageResponse markFailed(MarkWhatsAppMessageFailedRequest request);

    List<WhatsAppIncomingMessageResponse> getUnprocessedMessages();
}
