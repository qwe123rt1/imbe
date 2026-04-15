package com.rohit.clinic.service;

import com.rohit.clinic.dto.request.CreateIncomingLeadFromWhatsAppRequest;
import com.rohit.clinic.dto.response.IncomingLeadResponse;

public interface IncomingLeadService {

    IncomingLeadResponse createFromWhatsApp(CreateIncomingLeadFromWhatsAppRequest request);
}
