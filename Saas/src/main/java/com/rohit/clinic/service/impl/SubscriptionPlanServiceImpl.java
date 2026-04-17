package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.request.CreateSubscriptionPlanRequest;
import com.rohit.clinic.dto.response.SubscriptionPlanResponse;
import com.rohit.clinic.entity.SubscriptionPlan;
import com.rohit.clinic.exception.BadRequestException;
import com.rohit.clinic.exception.ConflictException;
import com.rohit.clinic.exception.NotFoundException;
import com.rohit.clinic.repository.SubscriptionPlanRepository;
import com.rohit.clinic.service.SubscriptionPlanService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanServiceImpl(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Override
    @Transactional
    public SubscriptionPlanResponse createPlan(CreateSubscriptionPlanRequest request) {
        validateCreateRequest(request);

        String planCode = request.getPlanCode().trim().toUpperCase();
        if (subscriptionPlanRepository.findByPlanCodeIgnoreCase(planCode).isPresent()) {
            throw new ConflictException("Plan code is already registered");
        }

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setPlanCode(planCode);
        plan.setPlanName(request.getPlanName().trim());
        plan.setMonthlyPrice(request.getMonthlyPrice());
        plan.setLeadLimit(request.getLeadLimit());
        plan.setMatchLimit(request.getMatchLimit());
        plan.setUserLimit(request.getUserLimit());
        plan.setIsActive(request.getIsActive() == null || Boolean.TRUE.equals(request.getIsActive()));
        return toResponse(subscriptionPlanRepository.save(plan));
    }

    @Override
    public List<SubscriptionPlanResponse> getActivePlans() {
        return subscriptionPlanRepository.findByIsActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public SubscriptionPlanResponse getPlan(Long planId) {
        if (planId == null) {
            throw new BadRequestException("planId is required");
        }

        return subscriptionPlanRepository.findById(planId)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Subscription plan not found"));
    }

    private void validateCreateRequest(CreateSubscriptionPlanRequest request) {
        if (request == null) {
            throw new BadRequestException("Plan request is required");
        }
        if (request.getPlanCode() == null || request.getPlanCode().isBlank()) {
            throw new BadRequestException("planCode is required");
        }
        if (request.getPlanName() == null || request.getPlanName().isBlank()) {
            throw new BadRequestException("planName is required");
        }
        if (request.getMonthlyPrice() == null || request.getMonthlyPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("monthlyPrice must be zero or greater");
        }
    }

    private SubscriptionPlanResponse toResponse(SubscriptionPlan plan) {
        SubscriptionPlanResponse response = new SubscriptionPlanResponse();
        response.setId(plan.getId());
        response.setPlanCode(plan.getPlanCode());
        response.setPlanName(plan.getPlanName());
        response.setMonthlyPrice(plan.getMonthlyPrice());
        response.setLeadLimit(plan.getLeadLimit());
        response.setMatchLimit(plan.getMatchLimit());
        response.setUserLimit(plan.getUserLimit());
        response.setIsActive(plan.getIsActive());
        return response;
    }
}
