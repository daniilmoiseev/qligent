package com.complexas.listener;

import com.complexas.EnrichDto;
import com.complexas.ParcelDto;
import com.complexas.service.AugService;
import com.complexas.store.EnrichmentStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableKafka
public class Listener {

    private final EnrichmentStore enrichmentStore;
    private final AugService augService;

    @Autowired
    public Listener(EnrichmentStore enrichmentStore, AugService augService) {
        this.enrichmentStore = enrichmentStore;
        this.augService = augService;
    }

    @KafkaListener(topics = "${kafka.parcel-raw-topic}",
            containerFactory = "kafkaListenerContainerFactory1")
    public void onMessage(ParcelDto message) {
        log.info("Received message: {}", message);
        augService.enrich(message);
    }

    @KafkaListener(topics = "${kafka.enrich-raw-topic}",
            containerFactory = "kafkaListenerContainerFactory2")
    public void onMessage(EnrichDto message) {
        log.info("Received message: {}", message);
        enrichmentStore.put(message);
    }
}
