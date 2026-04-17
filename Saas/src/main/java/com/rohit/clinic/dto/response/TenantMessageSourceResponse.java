package com.rohit.clinic.dto.response;

import com.rohit.clinic.entity.MessageSourceType;
import com.rohit.clinic.entity.PermissionStatus;

public class TenantMessageSourceResponse {

    private Long id;
    private Long tenantId;
    private MessageSourceType sourceType;
    private String sourceLabel;
    private String phoneNumber;
    private PermissionStatus permissionStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public MessageSourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(MessageSourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceLabel() {
        return sourceLabel;
    }

    public void setSourceLabel(String sourceLabel) {
        this.sourceLabel = sourceLabel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PermissionStatus getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(PermissionStatus permissionStatus) {
        this.permissionStatus = permissionStatus;
    }
}
