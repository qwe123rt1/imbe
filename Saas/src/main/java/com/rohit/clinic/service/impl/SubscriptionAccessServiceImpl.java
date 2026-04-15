package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.response.SubscriptionAccessResult;
import com.rohit.clinic.entity.Subscription;
import com.rohit.clinic.entity.SubscriptionStatus;
import com.rohit.clinic.repository.SubscriptionRepository;
import com.rohit.clinic.service.PlanEntitlementService;
import com.rohit.clinic.service.SubscriptionAccessService;
import com.rohit.clinic.service.SubscriptionUsageService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionAccessServiceImpl implements SubscriptionAccessService {

    private static final String LEADS_PER_MONTH = "LEADS_PER_MONTH";
    private static final String MATCHES_PER_LEAD = "MATCHES_PER_LEAD";

    private final SubscriptionRepository subscriptionRepository;
    private final PlanEntitlementService planEntitlementService;
    private final SubscriptionUsageService subscriptionUsageService;

    public SubscriptionAccessServiceImpl(
            SubscriptionRepository subscriptionRepository,
            PlanEntitlementService planEntitlementService,
            SubscriptionUsageService subscriptionUsageService
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.planEntitlementService = planEntitlementService;
        this.subscriptionUsageService = subscriptionUsageService;
    }

    @Override
    public SubscriptionAccessResult checkLeadTriggerAccess(Long tenantId) {
        if (tenantId == null) {
            return blocked("tenantId is required");
        }

        return subscriptionRepository.findFirstByTenant_IdAndStatusOrderByStartDateDesc(tenantId, SubscriptionStatus.ACTIVE)
                .map(this::buildAccessResult)
                .orElseGet(() -> blocked("No active subscription found"));
    }

    @Override
    public void recordLeadTriggered(Long subscriptionId) {
        subscriptionUsageService.incrementLeadTriggerCount(subscriptionId);
    }

    private SubscriptionAccessResult buildAccessResult(Subscription subscription) {
        Long planId = subscription.getPlan().getId();
        Integer monthlyLeadLimit = planEntitlementService.getFeatureLimit(planId, LEADS_PER_MONTH);
        Integer matchLimit = planEntitlementService.getFeatureLimit(planId, MATCHES_PER_LEAD);
        boolean canTrigger = subscriptionUsageService.canTriggerLead(subscription.getId(), monthlyLeadLimit);

        SubscriptionAccessResult result = new SubscriptionAccessResult();
        result.setSubscriptionId(subscription.getId());
        result.setMonthlyLeadLimit(monthlyLeadLimit);
        result.setMatchLimit(matchLimit);
        result.setAllowed(canTrigger);
        result.setReason(canTrigger ? "Allowed" : "Monthly lead trigger limit reached");
        return result;
    }

    private SubscriptionAccessResult blocked(String reason) {
        SubscriptionAccessResult result = new SubscriptionAccessResult();
        result.setAllowed(false);
        result.setReason(reason);
        return result;
    }
}
