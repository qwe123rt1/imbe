package com.rohit.clinic.service.impl;

import com.rohit.clinic.entity.MessageSourceType;
import com.rohit.clinic.entity.PermissionStatus;
import com.rohit.clinic.entity.Tenant;
import com.rohit.clinic.entity.TenantMessageSource;
import com.rohit.clinic.entity.TenantStatus;
import com.rohit.clinic.exception.BadRequestException;
import com.rohit.clinic.exception.NotFoundException;
import com.rohit.clinic.repository.TenantMessageSourceRepository;
import com.rohit.clinic.repository.TenantRepository;
import com.rohit.clinic.service.TenantMessageSourceService;
import java.util.List;
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
        validateRegisterRequest(tenantId, phoneNumber);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
        if (tenant.getStatus() != TenantStatus.ACTIVE) {
            throw new BadRequestException("Tenant is not active");
        }

        String normalizedPhone = phoneNumber.trim();
        return tenantMessageSourceRepository.findByTenant_IdAndPhoneNumber(tenantId, normalizedPhone)
                .orElseGet(() -> createSource(tenant, normalizedPhone, sourceLabel));
    }

    @Override
    public List<TenantMessageSource> getSources(Long tenantId) {
        if (tenantId == null) {
            throw new BadRequestException("tenantId is required");
        }
        if (!tenantRepository.existsById(tenantId)) {
            throw new NotFoundException("Tenant not found");
        }
        return tenantMessageSourceRepository.findByTenant_Id(tenantId);
    }

    private void validateRegisterRequest(Long tenantId, String phoneNumber) {
        if (tenantId == null) {
            throw new BadRequestException("tenantId is required");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new BadRequestException("phoneNumber is required");
        }
    }

    private TenantMessageSource createSource(Tenant tenant, String phoneNumber, String sourceLabel) {
        TenantMessageSource source = new TenantMessageSource();
        source.setTenant(tenant);
        source.setSourceType(MessageSourceType.WHATSAPP_NOTIFICATION_READER);
        source.setSourceLabel(sourceLabel != null && !sourceLabel.isBlank() ? sourceLabel.trim() : "WhatsApp Reader");
        source.setPhoneNumber(phoneNumber);
        source.setPermissionStatus(PermissionStatus.GRANTED);
        return tenantMessageSourceRepository.save(source);
    }
}
