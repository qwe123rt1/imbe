package com.rohit.clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscription_usage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionUsage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @Column(name = "usage_month", nullable = false, length = 7)
    private String usageMonth;

    @Column(name = "lead_trigger_count", nullable = false)
    private Integer leadTriggerCount;

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public String getUsageMonth() {
        return usageMonth;
    }

    public void setUsageMonth(String usageMonth) {
        this.usageMonth = usageMonth;
    }

    public Integer getLeadTriggerCount() {
        return leadTriggerCount;
    }

    public void setLeadTriggerCount(Integer leadTriggerCount) {
        this.leadTriggerCount = leadTriggerCount;
    }
}
