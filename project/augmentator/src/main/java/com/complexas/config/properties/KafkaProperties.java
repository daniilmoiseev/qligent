package com.complexas.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("kafka")
@Data
public class KafkaProperties {
    private String parcelRawTopic;
    private String enrichRawTopic;
    private String completeOutTopic;
    private String parcelDropTopic;
    private String bootstrapServers;
}
