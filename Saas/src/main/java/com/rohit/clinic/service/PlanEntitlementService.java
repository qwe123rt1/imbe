package com.rohit.clinic.service;

public interface PlanEntitlementService {

    boolean isFeatureEnabled(Long planId, String featureCode);

    Integer getFeatureLimit(Long planId, String featureCode);
}
