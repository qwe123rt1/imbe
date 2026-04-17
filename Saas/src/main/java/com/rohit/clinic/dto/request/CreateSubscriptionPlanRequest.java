package com.rohit.clinic.dto.request;

import java.math.BigDecimal;

public class CreateSubscriptionPlanRequest {

    private String planCode;
    private String planName;
    private BigDecimal monthlyPrice;
    private Integer leadLimit;
    private Integer matchLimit;
    private Integer userLimit;
    private Boolean isActive;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public Integer getLeadLimit() {
        return leadLimit;
    }

    public void setLeadLimit(Integer leadLimit) {
        this.leadLimit = leadLimit;
    }

    public Integer getMatchLimit() {
        return matchLimit;
    }

    public void setMatchLimit(Integer matchLimit) {
        this.matchLimit = matchLimit;
    }

    public Integer getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(Integer userLimit) {
        this.userLimit = userLimit;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }
}
