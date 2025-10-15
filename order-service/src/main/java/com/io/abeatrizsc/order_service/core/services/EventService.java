package com.io.abeatrizsc.order_service.core.services;

import com.io.abeatrizsc.order_service.config.exceptions.ValidationException;
import com.io.abeatrizsc.order_service.core.dtos.EventFilters;
import com.io.abeatrizsc.order_service.core.entities.Event;
import com.io.abeatrizsc.order_service.core.repositories.EventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository repository;

    public Event save(Event event) {
        return repository.save(event);
    }

    public void notifyEnding(Event event) {
        event.setOrderId(event.getOrderId());
        event.setCreatedAt(LocalDateTime.now());
        save(event);
        log.info("Order {} with saga notified! Transaction id: {}", event.getOrderId(), event.getTransactionId());
    }

    public List<Event> getAll() {
        return repository.findAllByOrderByCreatedAtDesc() ;
    }

    public Event getByFilters(EventFilters filters) {
        validateEmptyFilters(filters);
        if (!filters.orderId().isEmpty()) {
            return findByOrderId(filters.orderId());
        } else {
            return findByTransactionId(filters.transactionId());
        }
    }

    private void validateEmptyFilters(EventFilters filters) {
        if (filters.orderId().isEmpty() && filters.transactionId().isEmpty()) {
            throw new ValidationException("OrderID or TransactionID must be informed.");
        }
    }

    private Event findByTransactionId(String transactionId) {
        return repository
                .findTop1ByTransactionIdOrderByCreatedAtDesc(transactionId)
                .orElseThrow(() -> new ValidationException("Event not found by transactionId."));
    }

    private Event findByOrderId(String orderId) {
        return repository
                .findTop1ByOrderIdOrderByCreatedAtDesc(orderId)
                .orElseThrow(() -> new ValidationException("Event not found by orderID."));
    }
}
