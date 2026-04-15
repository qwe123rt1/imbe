package com.rohit.clinic.service;

import com.rohit.clinic.dto.request.DetectedMessageRequest;
import com.rohit.clinic.dto.response.ProcessWhatsAppMessageResponse;

public interface DetectedMessageService {

    ProcessWhatsAppMessageResponse processDetectedMessage(DetectedMessageRequest request);
}
