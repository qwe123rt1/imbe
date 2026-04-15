package com.rohit.clinic.dto.response;

public class SubscriptionAccessResult {

    private Long subscriptionId;
    private boolean allowed;
    private String reason;
    private Integer monthlyLeadLimit;
    private Integer matchLimit;

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getMonthlyLeadLimit() {
        return monthlyLeadLimit;
    }

    public void setMonthlyLeadLimit(Integer monthlyLeadLimit) {
        this.monthlyLeadLimit = monthlyLeadLimit;
    }

    public Integer getMatchLimit() {
        return matchLimit;
    }

    public void setMatchLimit(Integer matchLimit) {
        this.matchLimit = matchLimit;
    }
}
