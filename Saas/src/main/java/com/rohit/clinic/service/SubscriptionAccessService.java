package com.rohit.clinic.service;

import com.rohit.clinic.dto.response.SubscriptionAccessResult;

public interface SubscriptionAccessService {

    SubscriptionAccessResult checkLeadTriggerAccess(Long tenantId);

    void recordLeadTriggered(Long subscriptionId);
}
