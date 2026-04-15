package com.rohit.clinic.service;

import com.rohit.clinic.dto.request.ProcessWhatsAppMessageRequest;
import com.rohit.clinic.dto.response.ProcessWhatsAppMessageResponse;

public interface InboundLeadProcessingService {

    ProcessWhatsAppMessageResponse processIncomingWhatsAppMessage(ProcessWhatsAppMessageRequest request);
}
