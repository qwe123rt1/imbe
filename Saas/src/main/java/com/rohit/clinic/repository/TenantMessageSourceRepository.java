package com.rohit.clinic.repository;

import com.rohit.clinic.entity.TenantMessageSource;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantMessageSourceRepository extends JpaRepository<TenantMessageSource, Long> {

    List<TenantMessageSource> findByTenant_Id(Long tenantId);

    Optional<TenantMessageSource> findByTenant_IdAndPhoneNumber(Long tenantId, String phoneNumber);
}
