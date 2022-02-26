package com.complexas.config;

import com.complexas.EnrichDto;
import com.complexas.config.properties.KafkaProperties;
import com.complexas.config.properties.ProducerProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;
    private final ProducerProperties producerProperties;

    public KafkaConfig(KafkaProperties kafkaProperties,
            ProducerProperties producerProperties) {
        this.kafkaProperties = kafkaProperties;
        this.producerProperties = producerProperties;
    }

    @Bean
    public KafkaTemplate<String, EnrichDto> enrichProducerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
    }
}
