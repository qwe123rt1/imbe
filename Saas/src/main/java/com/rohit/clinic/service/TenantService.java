package com.rohit.clinic.service;

import com.rohit.clinic.dto.request.RegisterTenantRequest;
import com.rohit.clinic.dto.request.UpdateTenantRequest;
import com.rohit.clinic.dto.response.TenantResponse;
import java.util.List;

public interface TenantService {

    TenantResponse registerTenant(RegisterTenantRequest request);

    List<TenantResponse> getTenants();

    TenantResponse getTenant(Long tenantId);

    TenantResponse updateTenant(Long tenantId, UpdateTenantRequest request);

    TenantResponse deleteTenant(Long tenantId);
}
