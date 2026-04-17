package com.rohit.clinic.controller;

import com.rohit.clinic.dto.request.ChooseSubscriptionPlanRequest;
import com.rohit.clinic.dto.response.SubscriptionResponse;
import com.rohit.clinic.service.SubscriptionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants/{tenantId}/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/choose-plan")
    public SubscriptionResponse choosePlan(
            @PathVariable Long tenantId,
            @RequestBody ChooseSubscriptionPlanRequest request
    ) {
        return subscriptionService.choosePlan(tenantId, request);
    }

    @GetMapping
    public List<SubscriptionResponse> getTenantSubscriptions(@PathVariable Long tenantId) {
        return subscriptionService.getTenantSubscriptions(tenantId);
    }
}
