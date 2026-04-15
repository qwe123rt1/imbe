package com.rohit.clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscription_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan extends BaseEntity {

    @Column(name = "plan_code", nullable = false, length = 100)
    private String planCode;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "monthly_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal monthlyPrice;

    @Column(name = "lead_limit")
    private Integer leadLimit;

    @Column(name = "match_limit")
    private Integer matchLimit;

    @Column(name = "user_limit")
    private Integer userLimit;

    @Column(name = "is_active")
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

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
