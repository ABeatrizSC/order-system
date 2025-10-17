package com.io.abeatrizsc.inventory_service.core.repositories;
import com.io.abeatrizsc.inventory_service.core.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findByProductCode(String productCode);
}