package com.io.abeatrizsc.inventory_service.core.repositories;

import com.io.abeatrizsc.inventory_service.core.entities.OrderInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderInventoryRepository extends JpaRepository<OrderInventory, Integer> {

    List<OrderInventory> findByOrderIdAndTransactionId(String orderId, String transactionId);
    Boolean existsByOrderIdAndTransactionId(String orderId, String transactionId);
}