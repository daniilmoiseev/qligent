package com.complexas.service;

import com.complexas.EnrichDto;
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
    private final KafkaTemplate<String, EnrichDto> kafkaTemplate;

    @Value("${kafka.enrich-raw-topic}")
    private String topic;

    public Producer(KafkaTemplate<String, EnrichDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String key, EnrichDto enrichDto) {
        logger.info("### -> Producing: " + enrichDto.getId());
        ListenableFuture<SendResult<String, EnrichDto>> future = this.kafkaTemplate.send(topic, key, enrichDto);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message = {} dut to: {}", enrichDto, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, EnrichDto> result) {
                log.info("Sent Message = {} with offset = {}", enrichDto, result.getRecordMetadata().offset());
            }
        });
    }

    public void sendMessage(EnrichDto enrichDto) {
        logger.info("### -> Producing: " + enrichDto.getId());
        ListenableFuture<SendResult<String, EnrichDto>> future = this.kafkaTemplate.send(topic, enrichDto);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message = {} dut to: {}", enrichDto, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, EnrichDto> result) {
                log.info("Sent Message = {} with offset = {}", enrichDto, result.getRecordMetadata().offset());
            }
        });
    }
}
