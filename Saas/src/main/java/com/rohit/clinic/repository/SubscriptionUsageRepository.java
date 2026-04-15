package com.rohit.clinic.repository;

import com.rohit.clinic.entity.SubscriptionUsage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionUsageRepository extends JpaRepository<SubscriptionUsage, Long> {

    Optional<SubscriptionUsage> findBySubscription_IdAndUsageMonth(Long subscriptionId, String usageMonth);
}
