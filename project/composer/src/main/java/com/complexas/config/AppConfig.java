package com.complexas.config;

import com.complexas.service.EnrichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableConfigurationProperties
@EnableScheduling
public class AppConfig {

    private final EnrichService enrichService;

    @Autowired
    public AppConfig(EnrichService enrichService) {
        this.enrichService = enrichService;
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 300000)
    private void compose() {
        enrichService.pushToKafka();
    }
}
