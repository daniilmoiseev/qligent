package com.example.sqm.controller;

import com.qligent.sqm.alert.data.model.AlertHistoryMessage.AlertState;
import com.qligent.sqm.alert.data.model.AlertProperties;
import com.qligent.sqm.alert.data.model.SimpleAlert;
import com.qligent.sqm.notifications.data.model.CommonAlertNotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/alert-message")
public class SqmController {
    private KafkaTemplate<String, CommonAlertNotificationMessage> template;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Autowired
    private CommonAlertNotificationMessage alertMessage;

    @GetMapping("/send-message")
    public String sendMessage(@RequestParam AlertState state) {
        alertMessage.setExtras(new HashMap<String, String>() {{
            put("key", "value");
        }});
        alertMessage.setAlertState(state);
        alertMessage.setAlert(SimpleAlert.builder().alertProperties(new AlertProperties()).build());
        template.send(topic, UUID.randomUUID().toString(), alertMessage);
        return "ok";
    }

    @GetMapping("/result-message")
    public String resultMessage() {
        return "";
    }

}
