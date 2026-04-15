package com.rohit.clinic.repository;

import com.rohit.clinic.entity.SubscriptionPlanFeature;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanFeatureRepository extends JpaRepository<SubscriptionPlanFeature, Long> {

    List<SubscriptionPlanFeature> findByPlan_IdAndIsEnabledTrue(Long planId);

    Optional<SubscriptionPlanFeature> findByPlan_IdAndFeature_FeatureCodeIgnoreCase(Long planId, String featureCode);
}
