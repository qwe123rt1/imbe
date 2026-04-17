package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.request.ChooseSubscriptionPlanRequest;
import com.rohit.clinic.dto.response.SubscriptionResponse;
import com.rohit.clinic.entity.BillingCycle;
import com.rohit.clinic.entity.Subscription;
import com.rohit.clinic.entity.SubscriptionPlan;
import com.rohit.clinic.entity.SubscriptionStatus;
import com.rohit.clinic.entity.Tenant;
import com.rohit.clinic.entity.TenantStatus;
import com.rohit.clinic.exception.BadRequestException;
import com.rohit.clinic.exception.NotFoundException;
import com.rohit.clinic.repository.SubscriptionPlanRepository;
import com.rohit.clinic.repository.SubscriptionRepository;
import com.rohit.clinic.repository.TenantRepository;
import com.rohit.clinic.service.SubscriptionService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final TenantRepository tenantRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(
            TenantRepository tenantRepository,
            SubscriptionPlanRepository subscriptionPlanRepository,
            SubscriptionRepository subscriptionRepository
    ) {
        this.tenantRepository = tenantRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @Transactional
    public SubscriptionResponse choosePlan(Long tenantId, ChooseSubscriptionPlanRequest request) {
        if (request == null) {
            throw new BadRequestException("Choose plan request is required");
        }

        Tenant tenant = getActiveTenant(tenantId);
        SubscriptionPlan plan = resolveActivePlan(request);
        BillingCycle billingCycle = request.getBillingCycle() != null ? request.getBillingCycle() : BillingCycle.MONTHLY;
        LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : LocalDate.now();

        closeCurrentSubscriptions(tenant.getId(), startDate);

        tenant.setMatchLimit(plan.getMatchLimit());
        tenant.setUserLimit(plan.getUserLimit());
        tenantRepository.save(tenant);

        Subscription subscription = new Subscription();
        subscription.setTenant(tenant);
        subscription.setPlan(plan);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartDate(startDate);
        subscription.setEndDate(calculateEndDate(startDate, billingCycle));
        subscription.setBillingCycle(billingCycle);
        return toResponse(subscriptionRepository.save(subscription));
    }

    @Override
    public List<SubscriptionResponse> getTenantSubscriptions(Long tenantId) {
        if (tenantId == null) {
            throw new BadRequestException("tenantId is required");
        }

        return subscriptionRepository.findByTenant_IdOrderByStartDateDesc(tenantId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Tenant getActiveTenant(Long tenantId) {
        if (tenantId == null) {
            throw new BadRequestException("tenantId is required");
        }

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
        if (tenant.getStatus() != TenantStatus.ACTIVE) {
            throw new BadRequestException("Tenant is not active");
        }
        return tenant;
    }

    private SubscriptionPlan resolveActivePlan(ChooseSubscriptionPlanRequest request) {
        SubscriptionPlan plan;
        if (request.getPlanId() != null) {
            plan = subscriptionPlanRepository.findById(request.getPlanId())
                    .orElseThrow(() -> new NotFoundException("Subscription plan not found"));
        } else if (request.getPlanCode() != null && !request.getPlanCode().isBlank()) {
            plan = subscriptionPlanRepository.findByPlanCodeIgnoreCase(request.getPlanCode().trim())
                    .orElseThrow(() -> new NotFoundException("Subscription plan not found"));
        } else {
            throw new BadRequestException("planId or planCode is required");
        }

        if (!Boolean.TRUE.equals(plan.getIsActive())) {
            throw new BadRequestException("Subscription plan is not active");
        }
        return plan;
    }

    private void closeCurrentSubscriptions(Long tenantId, LocalDate nextStartDate) {
        List<Subscription> subscriptions = subscriptionRepository.findByTenant_IdOrderByStartDateDesc(tenantId);
        for (Subscription subscription : subscriptions) {
            if (subscription.getStatus() == SubscriptionStatus.ACTIVE || subscription.getStatus() == SubscriptionStatus.TRIAL) {
                subscription.setStatus(SubscriptionStatus.CANCELLED);
                LocalDate endDate = nextStartDate.minusDays(1);
                if (subscription.getEndDate() == null || subscription.getEndDate().isAfter(endDate)) {
                    subscription.setEndDate(endDate);
                }
                subscriptionRepository.save(subscription);
            }
        }
    }

    private LocalDate calculateEndDate(LocalDate startDate, BillingCycle billingCycle) {
        if (billingCycle == BillingCycle.YEARLY) {
            return startDate.plusYears(1).minusDays(1);
        }
        return startDate.plusMonths(1).minusDays(1);
    }

    private SubscriptionResponse toResponse(Subscription subscription) {
        SubscriptionResponse response = new SubscriptionResponse();
        response.setId(subscription.getId());
        response.setTenantId(subscription.getTenant() != null ? subscription.getTenant().getId() : null);
        if (subscription.getPlan() != null) {
            response.setPlanId(subscription.getPlan().getId());
            response.setPlanCode(subscription.getPlan().getPlanCode());
            response.setPlanName(subscription.getPlan().getPlanName());
        }
        response.setStatus(subscription.getStatus());
        response.setStartDate(subscription.getStartDate());
        response.setEndDate(subscription.getEndDate());
        response.setBillingCycle(subscription.getBillingCycle());
        return response;
    }
}
