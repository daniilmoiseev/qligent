package com.complexas.config.properties;

import java.util.Properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("kafka.consumer")
@Data
public class ConsumerProperties {
    private String consumerGroup;
    private Properties properties;
}
