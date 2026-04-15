package com.rohit.clinic.repository;

import com.rohit.clinic.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIsActiveTrue();

    java.util.Optional<Product> findByProductNameIgnoreCaseAndIsActiveTrue(String productName);
}
