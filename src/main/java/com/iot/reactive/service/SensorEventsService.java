package com.iot.reactive.service;

import com.iot.reactive.dto.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ReplayProcessor;

@Service
public class SensorEventsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SensorEventsService.class.getName());
    private final ReplayProcessor<SensorData> sensorDataReplayProcessor;

    public SensorEventsService() {
        sensorDataReplayProcessor = ReplayProcessor.cacheLast();

    }

    public ReplayProcessor<SensorData> getProcessor() {
        return sensorDataReplayProcessor;
    }

    public void onNext(SensorData sensorData) {
        LOGGER.info("onNext [{}]", sensorData);
        sensorDataReplayProcessor.onNext(sensorData);
    }

    public void onComplete() {
        LOGGER.info("onComplete");
        sensorDataReplayProcessor.onComplete();
    }

    public void onError(Throwable t) {
        LOGGER.error("onError", t);
        sensorDataReplayProcessor.onError(t);
    }
}
