package com.rohit.clinic.repository;

import com.rohit.clinic.entity.Subscription;
import com.rohit.clinic.entity.SubscriptionStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findFirstByTenant_IdAndStatusOrderByStartDateDesc(Long tenantId, SubscriptionStatus status);

    List<Subscription> findByTenant_IdOrderByStartDateDesc(Long tenantId);
}
