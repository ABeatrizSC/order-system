package com.io.abeatrizsc.inventory_service.core.consumers;

import com.io.abeatrizsc.inventory_service.core.services.InventoryService;
import com.io.abeatrizsc.inventory_service.core.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryConsumer {
    private final JsonUtil jsonUtil;
    private final InventoryService service;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.inventory-success}"
    )
    public void consumeSuccessEvent(String payload) {
        log.info("Receiving success event {} from inventory-success topic", payload);
        var event = jsonUtil.toEvent(payload);
        service.updateInventory(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.inventory-fail}"
    )
    public void consumeFailEvent(String payload) {
        log.info("Receiving rollback event {} from inventory-fail topic", payload);
        var event = jsonUtil.toEvent(payload);
        service.rollbackInventory(event);
    }
}