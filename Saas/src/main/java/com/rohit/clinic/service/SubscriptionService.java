package com.rohit.clinic.service;

import com.rohit.clinic.dto.request.ChooseSubscriptionPlanRequest;
import com.rohit.clinic.dto.response.SubscriptionResponse;
import java.util.List;

public interface SubscriptionService {

    SubscriptionResponse choosePlan(Long tenantId, ChooseSubscriptionPlanRequest request);

    List<SubscriptionResponse> getTenantSubscriptions(Long tenantId);
}
