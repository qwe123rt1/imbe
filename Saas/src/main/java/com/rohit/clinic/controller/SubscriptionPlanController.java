package com.rohit.clinic.controller;

import com.rohit.clinic.dto.request.CreateSubscriptionPlanRequest;
import com.rohit.clinic.dto.response.SubscriptionPlanResponse;
import com.rohit.clinic.service.SubscriptionPlanService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription-plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    public SubscriptionPlanController(SubscriptionPlanService subscriptionPlanService) {
        this.subscriptionPlanService = subscriptionPlanService;
    }

    @PostMapping
    public SubscriptionPlanResponse createPlan(@RequestBody CreateSubscriptionPlanRequest request) {
        return subscriptionPlanService.createPlan(request);
    }

    @GetMapping
    public List<SubscriptionPlanResponse> getActivePlans() {
        return subscriptionPlanService.getActivePlans();
    }

    @GetMapping("/{planId}")
    public SubscriptionPlanResponse getPlan(@PathVariable Long planId) {
        return subscriptionPlanService.getPlan(planId);
    }
}
