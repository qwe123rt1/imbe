package com.rohit.clinic.service.impl;

import com.rohit.clinic.entity.SubscriptionPlanFeature;
import com.rohit.clinic.repository.SubscriptionPlanFeatureRepository;
import com.rohit.clinic.service.PlanEntitlementService;
import org.springframework.stereotype.Service;

@Service
public class PlanEntitlementServiceImpl implements PlanEntitlementService {

    private final SubscriptionPlanFeatureRepository subscriptionPlanFeatureRepository;

    public PlanEntitlementServiceImpl(SubscriptionPlanFeatureRepository subscriptionPlanFeatureRepository) {
        this.subscriptionPlanFeatureRepository = subscriptionPlanFeatureRepository;
    }

    @Override
    public boolean isFeatureEnabled(Long planId, String featureCode) {
        return findPlanFeature(planId, featureCode)
                .map(SubscriptionPlanFeature::getIsEnabled)
                .orElse(false);
    }

    @Override
    public Integer getFeatureLimit(Long planId, String featureCode) {
        return findPlanFeature(planId, featureCode)
                .filter(feature -> Boolean.TRUE.equals(feature.getIsEnabled()))
                .map(SubscriptionPlanFeature::getFeatureLimit)
                .orElse(null);
    }

    private java.util.Optional<SubscriptionPlanFeature> findPlanFeature(Long planId, String featureCode) {
        if (planId == null || featureCode == null || featureCode.isBlank()) {
            return java.util.Optional.empty();
        }

        return subscriptionPlanFeatureRepository.findByPlan_IdAndFeature_FeatureCodeIgnoreCase(planId, featureCode);
    }
}
