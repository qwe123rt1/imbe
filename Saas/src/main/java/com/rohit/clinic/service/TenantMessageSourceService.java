package com.rohit.clinic.service;

import com.rohit.clinic.entity.TenantMessageSource;

public interface TenantMessageSourceService {

    TenantMessageSource registerSource(Long tenantId, String phoneNumber, String sourceLabel);
}
