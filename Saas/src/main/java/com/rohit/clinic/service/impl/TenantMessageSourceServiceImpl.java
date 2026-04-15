package com.rohit.clinic.service.impl;

import com.rohit.clinic.entity.MessageSourceType;
import com.rohit.clinic.entity.PermissionStatus;
import com.rohit.clinic.entity.Tenant;
import com.rohit.clinic.entity.TenantMessageSource;
import com.rohit.clinic.exception.NotFoundException;
import com.rohit.clinic.repository.TenantMessageSourceRepository;
import com.rohit.clinic.repository.TenantRepository;
import com.rohit.clinic.service.TenantMessageSourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantMessageSourceServiceImpl implements TenantMessageSourceService {

    private final TenantRepository tenantRepository;
    private final TenantMessageSourceRepository tenantMessageSourceRepository;

    public TenantMessageSourceServiceImpl(
            TenantRepository tenantRepository,
            TenantMessageSourceRepository tenantMessageSourceRepository
    ) {
        this.tenantRepository = tenantRepository;
        this.tenantMessageSourceRepository = tenantMessageSourceRepository;
    }

    @Override
    @Transactional
    public TenantMessageSource registerSource(Long tenantId, String phoneNumber, String sourceLabel) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));

        return tenantMessageSourceRepository.findByTenant_IdAndPhoneNumber(tenantId, phoneNumber)
                .orElseGet(() -> createSource(tenant, phoneNumber, sourceLabel));
    }

    private TenantMessageSource createSource(Tenant tenant, String phoneNumber, String sourceLabel) {
        TenantMessageSource source = new TenantMessageSource();
        source.setTenant(tenant);
        source.setSourceType(MessageSourceType.WHATSAPP_NOTIFICATION_READER);
        source.setSourceLabel(sourceLabel);
        source.setPhoneNumber(phoneNumber);
        source.setPermissionStatus(PermissionStatus.GRANTED);
        return tenantMessageSourceRepository.save(source);
    }
}
