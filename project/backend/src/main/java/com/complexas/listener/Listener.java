package com.complexas.listener;

import com.complexas.CompleteDto;
import com.complexas.ParcelDto;
import com.complexas.dto.AllDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableKafka
public class Listener {

    @KafkaListener(topics = "${kafka.parcel-drop-topic}",
            containerFactory = "kafkaListenerContainerFactory1")
    public void onMessage(ParcelDto message) {
        log.info("Received message: {}", message);
        AllDto.parcelFromKafka.add(message);
    }

    @KafkaListener(topics = "${kafka.complete-out-topic}",
            containerFactory = "kafkaListenerContainerFactory2")
    public void onMessage(CompleteDto message) {
        log.info("Received message: {}", message);
        AllDto.completeFromKafka.add(message);
    }
}
