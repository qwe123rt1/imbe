package com.rohit.clinic.service;

import com.rohit.clinic.entity.TenantMessageSource;
import java.util.List;

public interface TenantMessageSourceService {

    TenantMessageSource registerSource(Long tenantId, String phoneNumber, String sourceLabel);

    List<TenantMessageSource> getSources(Long tenantId);
}
