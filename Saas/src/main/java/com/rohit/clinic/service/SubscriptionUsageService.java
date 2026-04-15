package com.rohit.clinic.service;

public interface SubscriptionUsageService {

    boolean canTriggerLead(Long subscriptionId, Integer monthlyLeadLimit);

    void incrementLeadTriggerCount(Long subscriptionId);
}
