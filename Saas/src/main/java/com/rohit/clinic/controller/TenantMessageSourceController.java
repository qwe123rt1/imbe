package com.rohit.clinic.controller;

import com.rohit.clinic.dto.request.RegisterTenantMessageSourceRequest;
import com.rohit.clinic.dto.response.TenantMessageSourceResponse;
import com.rohit.clinic.entity.TenantMessageSource;
import com.rohit.clinic.service.TenantMessageSourceService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants/{tenantId}/message-sources")
public class TenantMessageSourceController {

    private final TenantMessageSourceService tenantMessageSourceService;

    public TenantMessageSourceController(TenantMessageSourceService tenantMessageSourceService) {
        this.tenantMessageSourceService = tenantMessageSourceService;
    }

    @PostMapping
    public TenantMessageSourceResponse registerSource(
            @PathVariable Long tenantId,
            @RequestBody RegisterTenantMessageSourceRequest request
    ) {
        TenantMessageSource source = tenantMessageSourceService.registerSource(
                tenantId,
                request != null ? request.getPhoneNumber() : null,
                request != null ? request.getSourceLabel() : null
        );
        return toResponse(source);
    }

    @GetMapping
    public List<TenantMessageSourceResponse> getSources(@PathVariable Long tenantId) {
        return tenantMessageSourceService.getSources(tenantId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TenantMessageSourceResponse toResponse(TenantMessageSource source) {
        TenantMessageSourceResponse response = new TenantMessageSourceResponse();
        response.setId(source.getId());
        response.setTenantId(source.getTenant() != null ? source.getTenant().getId() : null);
        response.setSourceType(source.getSourceType());
        response.setSourceLabel(source.getSourceLabel());
        response.setPhoneNumber(source.getPhoneNumber());
        response.setPermissionStatus(source.getPermissionStatus());
        return response;
    }
}
