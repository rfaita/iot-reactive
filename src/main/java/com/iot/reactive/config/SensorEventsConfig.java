package com.iot.reactive.config;

import com.iot.reactive.dto.SensorData;
import com.iot.reactive.service.SensorEventsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

import java.util.function.Consumer;

@Configuration
public class SensorEventsConfig {

    @Bean
    public Consumer<Flux<SensorData>> sensorEvents(final SensorEventsService sensorEventsService) {

        return sensorEvents ->
                sensorEvents
                        .doOnNext(sensorEventsService::onNext)
                        .doOnError(sensorEventsService::onError)
                        .subscribe();
    }

}
