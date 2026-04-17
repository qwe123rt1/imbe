package com.rohit.clinic.dto.request;

public class SendWhatsAppTextMessageRequest {

    private String toPhone;
    private String messageBody;

    public String getToPhone() {
        return toPhone;
    }

    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
