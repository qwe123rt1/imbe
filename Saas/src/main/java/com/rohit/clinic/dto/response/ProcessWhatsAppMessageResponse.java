package com.rohit.clinic.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessWhatsAppMessageResponse {

    private Long leadId;
    private Integer matchCount;
    private String replyMessage;

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(Integer matchCount) {
        this.matchCount = matchCount;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }
}
