package com.rohit.clinic.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateIncomingLeadFromWhatsAppRequest {

    private Long whatsappMessageId;

    public Long getWhatsappMessageId() {
        return whatsappMessageId;
    }

    public void setWhatsappMessageId(Long whatsappMessageId) {
        this.whatsappMessageId = whatsappMessageId;
    }
}
