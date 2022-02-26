package com.complexas.config;

import com.complexas.CompleteDto;
import com.complexas.ParcelDto;
import com.complexas.config.properties.ConsumerProperties;
import com.complexas.config.properties.KafkaProperties;
import com.complexas.deserializer.CompleteDtoDeserializer;
import com.complexas.deserializer.ParcelDtoDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableKafka
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;
    private final ConsumerProperties consumerProperties;

    @Autowired
    public KafkaConfig(KafkaProperties kafkaProperties,
                       ConsumerProperties consumerProperties) {
        this.kafkaProperties = kafkaProperties;
        this.consumerProperties = consumerProperties;
    }

    public Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();

        Properties properties = consumerProperties.getProperties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerProperties.getConsumerGroup());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, Integer.parseInt(properties.getProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG)));
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, Integer.parseInt(properties.getProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG)));
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, Integer.parseInt(properties.getProperty(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG)));

        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, Integer.parseInt(properties.getProperty("max.partition.fetch.mb")) * 1024 * 1024);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, Integer.parseInt(properties.getProperty(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG)));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return props;
    }

    public ConsumerFactory<String, ParcelDto> consumerFactory1() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(),
                new ParcelDtoDeserializer());
    }

    @Bean(name = "kafkaListenerContainerFactory1")
    public ConcurrentKafkaListenerContainerFactory<String, ParcelDto> kafkaListenerContainerFactory1() {
        ConcurrentKafkaListenerContainerFactory<String, ParcelDto> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory1());
        factory.getContainerProperties().setAckMode(AckMode.RECORD);
        return factory;
    }

    public ConsumerFactory<String, CompleteDto> consumerFactory2() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(),
                new CompleteDtoDeserializer());
    }

    @Bean(name = "kafkaListenerContainerFactory2")
    public ConcurrentKafkaListenerContainerFactory<String, CompleteDto> kafkaListenerContainerFactory2() {
        ConcurrentKafkaListenerContainerFactory<String, CompleteDto> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory2());
        factory.getContainerProperties().setAckMode(AckMode.RECORD);
        return factory;
    }
}
