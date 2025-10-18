package com.io.abeatrizsc.orchestrator_service.core.services;

import com.io.abeatrizsc.orchestrator_service.core.dtos.Event;
import com.io.abeatrizsc.orchestrator_service.core.dtos.History;
import com.io.abeatrizsc.orchestrator_service.core.enums.ETopics;
import com.io.abeatrizsc.orchestrator_service.core.producers.SagaOrchestratorProducer;
import com.io.abeatrizsc.orchestrator_service.core.saga.SagaExecutionController;
import com.io.abeatrizsc.orchestrator_service.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.io.abeatrizsc.orchestrator_service.core.enums.EEventSource.ORCHESTRATOR;
import static com.io.abeatrizsc.orchestrator_service.core.enums.ESagaStatus.FAIL;
import static com.io.abeatrizsc.orchestrator_service.core.enums.ESagaStatus.SUCCESS;
import static com.io.abeatrizsc.orchestrator_service.core.enums.ETopics.NOTIFY_ENDING;

@Slf4j
@Service
@AllArgsConstructor
public class OrchestratorService {
    private final JsonUtil jsonUtil;
    private final SagaOrchestratorProducer producer;
    private final SagaExecutionController sagaExecutionController;

    public void startSaga(Event event)
    {
        event.setSource(ORCHESTRATOR); // Este evento inicia no orquestrador
        event.setStatus(SUCCESS); // Inicio como Sucesso

        var topic = getTopic(event); // PRODUCT-VALIDATION-SERVICE

        log.info("SAGA STARTED!");
        addHistory(event, "Saga started!");
        producer.sendEvent(jsonUtil.toJson(event), topic.getTopic());
    }

    public void finishSagaSuccess(Event event) {
        event.setSource(ORCHESTRATOR);
        event.setStatus(SUCCESS);

        log.info("SAGA FINISHED SUCCESSFULLY FOR EVENT {}!", event.getId());
        addHistory(event, "Saga finished successfully!");
        notifyFinishedSaga(event);
    }

    public void finishSagaFail(Event event) {
        event.setSource(ORCHESTRATOR);
        event.setStatus(FAIL);

        log.info("SAGA FINISHED WITH ERRORS FOR EVENT {}!", event.getId());
        addHistory(event, "Saga finished with errors!");
        notifyFinishedSaga(event);
    }

    public void continueSaga(Event event) {
        var topic = getTopic(event);

        log.info("SAGA CONTINUING FOR EVENT {}", event.getId());
        producer.sendEvent(jsonUtil.toJson(event), topic.getTopic());
    }

    private void notifyFinishedSaga(Event event) {
        producer.sendEvent(jsonUtil.toJson(event), NOTIFY_ENDING.getTopic());
    }

    private ETopics getTopic(Event event) {
        return sagaExecutionController.getNextTopic(event);
    }

    private void addHistory(Event event, String message) {
        var history = History
                .builder()
                .source(event.getSource())
                .status(event.getStatus())
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
        event.addToHistory(history);
    }
}