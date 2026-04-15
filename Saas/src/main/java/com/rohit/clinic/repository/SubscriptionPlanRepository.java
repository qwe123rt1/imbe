package com.rohit.clinic.repository;

import com.rohit.clinic.entity.SubscriptionPlan;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {

    Optional<SubscriptionPlan> findByPlanCodeIgnoreCase(String planCode);

    List<SubscriptionPlan> findByIsActiveTrue();
}
