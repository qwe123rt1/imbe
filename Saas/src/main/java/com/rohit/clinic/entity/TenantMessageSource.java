package com.rohit.clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tenant_message_sources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantMessageSource extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 100)
    private MessageSourceType sourceType;

    @Column(name = "source_label")
    private String sourceLabel;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_status", nullable = false, length = 50)
    private PermissionStatus permissionStatus;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
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
