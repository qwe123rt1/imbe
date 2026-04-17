package com.rohit.clinic.service;

import com.rohit.clinic.dto.request.CreateSubscriptionPlanRequest;
import com.rohit.clinic.dto.response.SubscriptionPlanResponse;
import java.util.List;

public interface SubscriptionPlanService {

    SubscriptionPlanResponse createPlan(CreateSubscriptionPlanRequest request);

    List<SubscriptionPlanResponse> getActivePlans();

    SubscriptionPlanResponse getPlan(Long planId);
}
