package com.io.abeatrizsc.product_validation_service.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.io.abeatrizsc.product_validation_service.core.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Boolean existsByCode(String code);
}
