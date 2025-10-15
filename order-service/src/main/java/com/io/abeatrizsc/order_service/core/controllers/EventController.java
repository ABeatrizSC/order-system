package com.io.abeatrizsc.order_service.core.controllers;

import com.io.abeatrizsc.order_service.core.dtos.EventFilters;
import com.io.abeatrizsc.order_service.core.entities.Event;
import com.io.abeatrizsc.order_service.core.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;

    @GetMapping
    public ResponseEntity<Event> findByFilters(EventFilters filters) {
        return ResponseEntity.ok(service.getByFilters(filters));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
