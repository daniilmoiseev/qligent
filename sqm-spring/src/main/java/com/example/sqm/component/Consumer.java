package com.example.sqm.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qligent.sqm.alert.data.model.AlertHistoryMessage.AlertState;
import com.qligent.sqm.alert.data.model.AlertProperties;
import com.qligent.sqm.notifications.data.model.CommonAlertNotificationMessage;
import org.apache.commons.text.StringSubstitutor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class Consumer {

    @Value("${spring.rise.url}")
    private String urlRise;

    @Value("${spring.cease.body}")
    private String urlCease;

    private String resolvedUrl;
    private StringSubstitutor sub;
    private ObjectMapper objectMapper;

    private RestTemplate restTemplate;

    @KafkaListener(topics = "topic")
    public void listenTopic(ConsumerRecord<String, CommonAlertNotificationMessage> record) throws JsonProcessingException {
        CommonAlertNotificationMessage alertMessage = record.value();
        Map<String, String> extras = alertMessage.getExtras();
        AlertProperties properties = alertMessage.getAlert().getAlertProperties();

        objectMapper = new ObjectMapper();
        restTemplate = new RestTemplate();

        if(alertMessage.getAlertState() == AlertState.RISE) {
            String extrasStr = URLEncoder.encode(objectMapper.writeValueAsString(extras), StandardCharsets.UTF_8);
            String propStr = URLEncoder.encode(objectMapper.writeValueAsString(properties), StandardCharsets.UTF_8);
            Map<String, String> subValue = new HashMap<>() {{
                put("extras", extrasStr);
                put("properties", propStr);
            }};
            sub = new StringSubstitutor(subValue);
            resolvedUrl = sub.replace(urlRise);

            ResponseEntity<String> entity = restTemplate.getForEntity(resolvedUrl, String.class);
            restTemplate.exchange(resolvedUrl, HttpMethod.GET, entity, String.class);
        } else {
            String extrasStr = objectMapper.writeValueAsString(extras);
            String propStr = objectMapper.writeValueAsString(properties);
            Map<String, String> subValue = new HashMap<>() {{
                put("extras", extrasStr);
                put("properties", propStr);
            }};
            sub = new StringSubstitutor(subValue);
            resolvedUrl = sub.replace(urlCease);

            HttpEntity<String> entity = new HttpEntity<>("entity");
            restTemplate.exchange(resolvedUrl, HttpMethod.POST, entity, String.class);
        }


    }

}
