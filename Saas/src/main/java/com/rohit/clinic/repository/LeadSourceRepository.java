package com.rohit.clinic.repository;

import com.rohit.clinic.entity.LeadSource;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadSourceRepository extends JpaRepository<LeadSource, Long> {

    Optional<LeadSource> findBySourceCodeIgnoreCase(String sourceCode);
}
