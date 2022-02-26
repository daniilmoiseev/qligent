package com.complexas.service;

import com.complexas.ParcelDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class Producer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);
    private final KafkaTemplate<String, ParcelDto> kafkaTemplate;

    @Value("${kafka.parcel-raw-topic}")
    private String topic;

    public Producer(KafkaTemplate<String, ParcelDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String key, ParcelDto parcelDto) {
        logger.info("### -> Producing: " + parcelDto.getId());
        ListenableFuture<SendResult<String, ParcelDto>> future = this.kafkaTemplate.send(topic, key, parcelDto);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message = {} dut to: {}", parcelDto, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ParcelDto> result) {
                log.info("Sent Message = {} with offset = {}", parcelDto, result.getRecordMetadata().offset());
            }
        });
    }

    public void sendMessage(ParcelDto parcelDto) {
        logger.info("### -> Producing: " + parcelDto.getId());
        ListenableFuture<SendResult<String, ParcelDto>> future = this.kafkaTemplate.send(topic, parcelDto);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message = {} dut to: {}", parcelDto, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ParcelDto> result) {
                log.info("Sent Message = {} with offset = {}", parcelDto, result.getRecordMetadata().offset());
            }
        });
    }
}
