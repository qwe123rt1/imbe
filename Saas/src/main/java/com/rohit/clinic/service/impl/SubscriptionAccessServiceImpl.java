package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.response.SubscriptionAccessResult;
import com.rohit.clinic.entity.Subscription;
import com.rohit.clinic.entity.SubscriptionStatus;
import com.rohit.clinic.entity.TenantStatus;
import com.rohit.clinic.repository.SubscriptionRepository;
import com.rohit.clinic.service.PlanEntitlementService;
import com.rohit.clinic.service.SubscriptionAccessService;
import com.rohit.clinic.service.SubscriptionUsageService;
import java.time.LocalDate;
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

        return subscriptionRepository.findByTenant_IdOrderByStartDateDesc(tenantId)
                .stream()
                .filter(this::isUsableSubscription)
                .findFirst()
                .map(this::buildAccessResult)
                .orElseGet(() -> blocked("No usable subscription found"));
    }

    @Override
    public void recordLeadTriggered(Long subscriptionId) {
        subscriptionUsageService.incrementLeadTriggerCount(subscriptionId);
    }

    private SubscriptionAccessResult buildAccessResult(Subscription subscription) {
        Long planId = subscription.getPlan().getId();
        Integer monthlyLeadLimit = resolveFeatureLimit(planId, LEADS_PER_MONTH, subscription.getPlan().getLeadLimit());
        Integer matchLimit = resolveFeatureLimit(planId, MATCHES_PER_LEAD, subscription.getPlan().getMatchLimit());
        boolean canTrigger = subscriptionUsageService.canTriggerLead(subscription.getId(), monthlyLeadLimit);

        SubscriptionAccessResult result = new SubscriptionAccessResult();
        result.setSubscriptionId(subscription.getId());
        result.setMonthlyLeadLimit(monthlyLeadLimit);
        result.setMatchLimit(matchLimit);
        result.setAllowed(canTrigger);
        result.setReason(canTrigger ? "Allowed" : "Monthly lead trigger limit reached");
        return result;
    }

    private boolean isUsableSubscription(Subscription subscription) {
        if (subscription == null || subscription.getPlan() == null || subscription.getTenant() == null) {
            return false;
        }
        if (!isAllowedStatus(subscription.getStatus())) {
            return false;
        }
        if (subscription.getTenant().getStatus() != TenantStatus.ACTIVE) {
            return false;
        }
        if (!Boolean.TRUE.equals(subscription.getPlan().getIsActive())) {
            return false;
        }

        LocalDate today = LocalDate.now();
        boolean started = subscription.getStartDate() == null || !subscription.getStartDate().isAfter(today);
        boolean notEnded = subscription.getEndDate() == null || !subscription.getEndDate().isBefore(today);
        return started && notEnded;
    }

    private boolean isAllowedStatus(SubscriptionStatus status) {
        return status == SubscriptionStatus.ACTIVE || status == SubscriptionStatus.TRIAL;
    }

    private Integer resolveFeatureLimit(Long planId, String featureCode, Integer planColumnLimit) {
        Integer featureLimit = planEntitlementService.getFeatureLimit(planId, featureCode);
        return featureLimit != null ? featureLimit : planColumnLimit;
    }

    private SubscriptionAccessResult blocked(String reason) {
        SubscriptionAccessResult result = new SubscriptionAccessResult();
        result.setAllowed(false);
        result.setReason(reason);
        return result;
    }
}
