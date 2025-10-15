package com.io.abeatrizsc.order_service.core.services;

import com.io.abeatrizsc.order_service.core.dtos.OrderRequest;
import com.io.abeatrizsc.order_service.core.entities.Event;
import com.io.abeatrizsc.order_service.core.entities.Order;
import com.io.abeatrizsc.order_service.core.producers.SagaProducer;
import com.io.abeatrizsc.order_service.core.repositories.OrderRepository;
import com.io.abeatrizsc.order_service.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final EventService eventService;
    private final SagaProducer sagaProducer;
    private final JsonUtil jsonUtil;

    private static final String TRANSACTION_ID_PATTERN = "%s_%s";

    public Order createOrder(OrderRequest request) {
        var order = Order
                .builder()
                .products(request.products())
                .createdAt(LocalDateTime.now())
                .transactionId(
                        String.format(TRANSACTION_ID_PATTERN, Instant.now().toEpochMilli(), UUID.randomUUID())
                )
                .build();

        repository.save(order);
        sagaProducer.sendEvent(jsonUtil.toJson(createPayload(order)));

        return order;
    }

    private Event createPayload(Order order)  {
        var event = Event
                .builder()
                .orderId(order.getId())
                .transactionId(order.getTransactionId())
                .payload(order)
                .createdAt(LocalDateTime.now())
                .build();

        return eventService.save(event);
    }
}
