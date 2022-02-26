/*
package com.example.sqm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import com.qligent.sqm.*;
import javax.management.Notification;

@Configuration
@EnableConfigurationProperties
//@ConfigurationProperties(prefix = "sql")
public class SampleConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Notification> sqmFilterKafkaContainerFactory(
            ConsumerFactory<String, Notification> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Notification> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory);
        factory.setRecordFilterStrategy(sqmFilterStrategy());
        factory.getContainerProperties().setPollTimeout(5000);
        return factory;
    }
}
*/
