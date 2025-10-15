package com.io.abeatrizsc.order_service.core.repositories;

import com.io.abeatrizsc.order_service.core.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
