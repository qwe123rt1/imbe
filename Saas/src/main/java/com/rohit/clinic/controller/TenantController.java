package com.rohit.clinic.controller;

import com.rohit.clinic.dto.request.RegisterTenantRequest;
import com.rohit.clinic.dto.request.UpdateTenantRequest;
import com.rohit.clinic.dto.response.TenantResponse;
import com.rohit.clinic.service.TenantService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/register")
    public TenantResponse register(@RequestBody RegisterTenantRequest request) {
        return tenantService.registerTenant(request);
    }

    @PostMapping
    public TenantResponse createTenant(@RequestBody RegisterTenantRequest request) {
        return tenantService.registerTenant(request);
    }

    @GetMapping
    public List<TenantResponse> getTenants() {
        return tenantService.getTenants();
    }

    @GetMapping("/{tenantId}")
    public TenantResponse getTenant(@PathVariable Long tenantId) {
        return tenantService.getTenant(tenantId);
    }

    @PutMapping("/{tenantId}")
    public TenantResponse updateTenant(@PathVariable Long tenantId, @RequestBody UpdateTenantRequest request) {
        return tenantService.updateTenant(tenantId, request);
    }

    @DeleteMapping("/{tenantId}")
    public TenantResponse deleteTenant(@PathVariable Long tenantId) {
        return tenantService.deleteTenant(tenantId);
    }
}
