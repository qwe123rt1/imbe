package com.rohit.clinic.repository;

import com.rohit.clinic.entity.Feature;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Optional<Feature> findByFeatureCodeIgnoreCase(String featureCode);

    List<Feature> findByIsActiveTrue();
}
