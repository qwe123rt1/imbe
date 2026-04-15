package com.rohit.clinic.repository;

import com.rohit.clinic.entity.Tenant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByPhone(String phone);

    Optional<Tenant> findByEmailIgnoreCase(String email);
}
