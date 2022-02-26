package com.complexas.service;

import com.complexas.CompleteDto;
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
    private final KafkaTemplate<String, ParcelDto> parcelKafkaTemplate;
    private final KafkaTemplate<String, CompleteDto> completeKafkaTemplate;

    @Value("${kafka.parcel-drop-topic}")
    private String dropTopic;

    @Value("${kafka.complete-out-topic}")
    private String completeTopic;

    public Producer(KafkaTemplate<String, ParcelDto> parcelKafkaTemplate, KafkaTemplate<String, CompleteDto> completeKafkaTemplate) {
        this.parcelKafkaTemplate = parcelKafkaTemplate;
        this.completeKafkaTemplate = completeKafkaTemplate;
    }

    public void sendParcel(ParcelDto parcelDto) {
        logger.info("### -> Producing: " + parcelDto.getId());
        ListenableFuture<SendResult<String, ParcelDto>> future = this.parcelKafkaTemplate.send(dropTopic, parcelDto);

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

    public void sendComplete(CompleteDto completeDto) {
        logger.info("### -> Producing: " + completeDto.getId());
        ListenableFuture<SendResult<String, CompleteDto>> future = this.completeKafkaTemplate.send(completeTopic, completeDto);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message = {} dut to: {}", completeDto, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, CompleteDto> result) {
                log.info("Sent Message = {} with offset = {}", completeDto, result.getRecordMetadata().offset());
            }
        });
    }
}
