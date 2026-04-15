package com.rohit.clinic.service.impl;

import com.rohit.clinic.entity.Subscription;
import com.rohit.clinic.entity.SubscriptionUsage;
import com.rohit.clinic.exception.NotFoundException;
import com.rohit.clinic.repository.SubscriptionRepository;
import com.rohit.clinic.repository.SubscriptionUsageRepository;
import com.rohit.clinic.service.SubscriptionUsageService;
import java.time.YearMonth;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionUsageServiceImpl implements SubscriptionUsageService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionUsageRepository subscriptionUsageRepository;

    public SubscriptionUsageServiceImpl(
            SubscriptionRepository subscriptionRepository,
            SubscriptionUsageRepository subscriptionUsageRepository
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionUsageRepository = subscriptionUsageRepository;
    }

    @Override
    public boolean canTriggerLead(Long subscriptionId, Integer monthlyLeadLimit) {
        if (subscriptionId == null) {
            return false;
        }
        if (monthlyLeadLimit == null) {
            return true;
        }

        int currentCount = getCurrentUsage(subscriptionId).getLeadTriggerCount();
        return currentCount < monthlyLeadLimit;
    }

    @Override
    @Transactional
    public void incrementLeadTriggerCount(Long subscriptionId) {
        if (subscriptionId == null) {
            throw new IllegalArgumentException("subscriptionId is required");
        }

        SubscriptionUsage usage = getCurrentUsage(subscriptionId);
        usage.setLeadTriggerCount(usage.getLeadTriggerCount() + 1);
        subscriptionUsageRepository.save(usage);
    }

    private SubscriptionUsage getCurrentUsage(Long subscriptionId) {
        String usageMonth = YearMonth.now().toString();
        return subscriptionUsageRepository.findBySubscription_IdAndUsageMonth(subscriptionId, usageMonth)
                .orElseGet(() -> createCurrentUsage(subscriptionId, usageMonth));
    }

    private SubscriptionUsage createCurrentUsage(Long subscriptionId, String usageMonth) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));

        SubscriptionUsage usage = new SubscriptionUsage();
        usage.setSubscription(subscription);
        usage.setUsageMonth(usageMonth);
        usage.setLeadTriggerCount(0);
        return subscriptionUsageRepository.save(usage);
    }
}
