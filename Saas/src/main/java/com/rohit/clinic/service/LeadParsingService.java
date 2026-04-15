package com.rohit.clinic.service;

import com.rohit.clinic.dto.request.ParseWhatsAppMessageRequest;
import com.rohit.clinic.dto.response.ParsedLeadResponse;

public interface LeadParsingService {

    ParsedLeadResponse parseMessage(ParseWhatsAppMessageRequest request);
}
