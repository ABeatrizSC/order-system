package com.io.abeatrizsc.orchestrator_service.core.dtos;

import com.io.abeatrizsc.orchestrator_service.core.enums.EEventSource;
import com.io.abeatrizsc.orchestrator_service.core.enums.ESagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    private EEventSource source;
    private ESagaStatus status;
    private String message;
    private LocalDateTime createdAt;
}
