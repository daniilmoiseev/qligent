package com.complexas.config;

import com.complexas.CompleteDto;
import com.complexas.EnrichDto;
import com.complexas.ParcelDto;
import com.complexas.config.properties.ConsumerProperties;
import com.complexas.config.properties.KafkaProperties;
import com.complexas.config.properties.ProducerProperties;
import com.complexas.deserializer.ParcelDtoDeserializer;
import com.complexas.serializer.CompleteDtoSerializer;
import com.complexas.serializer.ParcelDtoSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final ProducerProperties producerProperties;

    public KafkaConfig(KafkaProperties kafkaProperties,
                       ConsumerProperties consumerProperties, ProducerProperties producerProperties) {
        this.kafkaProperties = kafkaProperties;
        this.consumerProperties = consumerProperties;
        this.producerProperties = producerProperties;
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

    public ConsumerFactory<String, EnrichDto> consumerFactory2() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(),
                new JsonDeserializer<>(EnrichDto.class));
    }

    @Bean(name = "kafkaListenerContainerFactory2")
    public ConcurrentKafkaListenerContainerFactory<String, EnrichDto> kafkaListenerContainerFactory2() {
        ConcurrentKafkaListenerContainerFactory<String, EnrichDto> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory2());
        factory.getContainerProperties().setAckMode(AckMode.RECORD);
        return factory;
    }

    @Bean
    public KafkaTemplate<String, ParcelDto> parcelProducerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ParcelDtoSerializer.class);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
    }

    @Bean
    public KafkaTemplate<String, CompleteDto> completeProducerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CompleteDtoSerializer.class);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
    }
}
