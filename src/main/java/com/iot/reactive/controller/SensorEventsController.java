package com.iot.reactive.controller;

import com.iot.reactive.dto.SensorData;
import com.iot.reactive.service.SensorEventsService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
public class SensorEventsController {

    private static final String X_TENANT_ID = "X-TenantId";
    private final SensorEventsService sensorEventsService;

    public SensorEventsController(SensorEventsService sensorEventsService) {
        this.sensorEventsService = sensorEventsService;
    }


    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<SensorData>> getEvents(@RequestHeader(X_TENANT_ID) String tenantId) {
        return this.sensorEventsService
                .getProcessor()
                .filter(sensorData -> tenantId.equalsIgnoreCase(sensorData.getTenantId()))
                .map(data -> ServerSentEvent.<SensorData>builder()
                        .data(data)
                        .id(UUID.randomUUID().toString())
                        .build());
    }

    @GetMapping(value = "/events/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<SensorData>> getEventsById(@RequestHeader(X_TENANT_ID) String tenantId,
                                                           @PathVariable String id) {
        return this.sensorEventsService
                .getProcessor()
                .filter(sensorData -> id.equalsIgnoreCase(sensorData.getId()))
                .filter(sensorData -> tenantId.equalsIgnoreCase(sensorData.getTenantId()))
                .map(data -> ServerSentEvent.<SensorData>builder()
                        .data(data)
                        .id(UUID.randomUUID().toString())
                        .build());
    }


}
