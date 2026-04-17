package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.request.RegisterTenantRequest;
import com.rohit.clinic.dto.request.UpdateTenantRequest;
import com.rohit.clinic.dto.response.TenantResponse;
import com.rohit.clinic.entity.Tenant;
import com.rohit.clinic.entity.TenantStatus;
import com.rohit.clinic.exception.BadRequestException;
import com.rohit.clinic.exception.ConflictException;
import com.rohit.clinic.exception.NotFoundException;
import com.rohit.clinic.repository.TenantRepository;
import com.rohit.clinic.service.TenantService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    @Transactional
    public TenantResponse registerTenant(RegisterTenantRequest request) {
        validateRegisterRequest(request);

        String phone = request.getPhone().trim();
        Tenant existingByPhone = tenantRepository.findByPhone(phone).orElse(null);
        if (existingByPhone != null) {
            if (existingByPhone.getStatus() == TenantStatus.DELETED) {
                String email = normalizeEmail(request.getEmail());
                validateUniqueEmail(email, existingByPhone.getId());
                existingByPhone.setBusinessName(request.getBusinessName().trim());
                existingByPhone.setOwnerName(trimToNull(request.getOwnerName()));
                existingByPhone.setEmail(email);
                existingByPhone.setStatus(TenantStatus.ACTIVE);
                return toResponse(tenantRepository.save(existingByPhone));
            }
            return toResponse(existingByPhone);
        }

        String email = normalizeEmail(request.getEmail());
        if (email != null && tenantRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new ConflictException("Tenant email is already registered");
        }

        Tenant tenant = new Tenant();
        tenant.setBusinessName(request.getBusinessName().trim());
        tenant.setOwnerName(trimToNull(request.getOwnerName()));
        tenant.setPhone(phone);
        tenant.setEmail(email);
        tenant.setStatus(TenantStatus.ACTIVE);
        return toResponse(tenantRepository.save(tenant));
    }

    @Override
    public List<TenantResponse> getTenants() {
        return tenantRepository.findByStatusNotOrderByIdDesc(TenantStatus.DELETED)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public TenantResponse getTenant(Long tenantId) {
        if (tenantId == null) {
            throw new BadRequestException("tenantId is required");
        }

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
        if (tenant.getStatus() == TenantStatus.DELETED) {
            throw new NotFoundException("Tenant not found");
        }
        return toResponse(tenant);
    }

    @Override
    @Transactional
    public TenantResponse updateTenant(Long tenantId, UpdateTenantRequest request) {
        if (request == null) {
            throw new BadRequestException("Tenant update request is required");
        }

        Tenant tenant = getActiveTenant(tenantId);

        if (request.getBusinessName() != null) {
            if (request.getBusinessName().isBlank()) {
                throw new BadRequestException("businessName cannot be blank");
            }
            tenant.setBusinessName(request.getBusinessName().trim());
        }
        if (request.getOwnerName() != null) {
            tenant.setOwnerName(trimToNull(request.getOwnerName()));
        }
        if (request.getPhone() != null) {
            String phone = request.getPhone().trim();
            if (phone.isBlank()) {
                throw new BadRequestException("phone cannot be blank");
            }
            validateUniquePhone(phone, tenant.getId());
            tenant.setPhone(phone);
        }
        if (request.getEmail() != null) {
            String email = normalizeEmail(request.getEmail());
            validateUniqueEmail(email, tenant.getId());
            tenant.setEmail(email);
        }
        if (request.getStatus() != null) {
            if (request.getStatus() == TenantStatus.DELETED) {
                throw new BadRequestException("Use DELETE to delete tenant");
            }
            tenant.setStatus(request.getStatus());
        }
        if (request.getMatchLimit() != null) {
            tenant.setMatchLimit(request.getMatchLimit());
        }
        if (request.getUserLimit() != null) {
            tenant.setUserLimit(request.getUserLimit());
        }

        return toResponse(tenantRepository.save(tenant));
    }

    @Override
    @Transactional
    public TenantResponse deleteTenant(Long tenantId) {
        Tenant tenant = getActiveTenant(tenantId);
        tenant.setStatus(TenantStatus.DELETED);
        return toResponse(tenantRepository.save(tenant));
    }

    private void validateRegisterRequest(RegisterTenantRequest request) {
        if (request == null) {
            throw new BadRequestException("Tenant registration request is required");
        }
        if (request.getBusinessName() == null || request.getBusinessName().isBlank()) {
            throw new BadRequestException("businessName is required");
        }
        if (request.getPhone() == null || request.getPhone().isBlank()) {
            throw new BadRequestException("phone is required");
        }
    }

    private String normalizeEmail(String email) {
        String value = trimToNull(email);
        return value != null ? value.toLowerCase() : null;
    }

    private String trimToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private Tenant getActiveTenant(Long tenantId) {
        if (tenantId == null) {
            throw new BadRequestException("tenantId is required");
        }

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
        if (tenant.getStatus() == TenantStatus.DELETED) {
            throw new NotFoundException("Tenant not found");
        }
        return tenant;
    }

    private void validateUniquePhone(String phone, Long tenantId) {
        Tenant existing = tenantRepository.findByPhone(phone).orElse(null);
        if (existing != null && !existing.getId().equals(tenantId)) {
            throw new ConflictException("Tenant phone is already registered");
        }
    }

    private void validateUniqueEmail(String email, Long tenantId) {
        if (email == null) {
            return;
        }

        Tenant existing = tenantRepository.findByEmailIgnoreCase(email).orElse(null);
        if (existing != null && !existing.getId().equals(tenantId)) {
            throw new ConflictException("Tenant email is already registered");
        }
    }

    private TenantResponse toResponse(Tenant tenant) {
        TenantResponse response = new TenantResponse();
        response.setId(tenant.getId());
        response.setBusinessName(tenant.getBusinessName());
        response.setOwnerName(tenant.getOwnerName());
        response.setPhone(tenant.getPhone());
        response.setEmail(tenant.getEmail());
        response.setStatus(tenant.getStatus());
        response.setMatchLimit(tenant.getMatchLimit());
        response.setUserLimit(tenant.getUserLimit());
        return response;
    }
}
