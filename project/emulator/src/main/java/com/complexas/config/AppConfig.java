package com.complexas.config;

import com.complexas.ParcelDto;
import com.complexas.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

@Configuration
@EnableConfigurationProperties
@EnableScheduling
public class AppConfig {

    private final List<String> types = List.of("Box", "Package", "Letter", "Telegram");
    private final List<String> names = List.of("Car", "Phone", "Toys", "Photo", "Laptop");
    private final List<String> fromPlace = List.of("Rom", "Moscow", "St.Petersburg", "Nizhniy Novgorod", "Samara");
    private final List<String> toPlace = List.of("Vladivostok", "Sarov", "Rostov", "Sochi", "Vladimir");

    private final Producer producer;

    @Autowired
    public AppConfig(Producer producer) {
        this.producer = producer;
    }

    @Scheduled(initialDelay = 90000, fixedDelay = 300000)
    private void push() {
        Random rnd = new Random();
        int count = rnd.nextInt(7) + 10;

        for (int i = 0; i < count; i++) {
            ParcelDto parcel = ParcelDto.builder()
                    .id(rnd.nextInt(1000))
                    .name(names.get(rnd.nextInt(5)))
                    .type(types.get(rnd.nextInt(4)))
                    .dateOfSending(ZonedDateTime.now())
                    .weight(rnd.nextInt(240) + 10)
                    .fromPlace(fromPlace.get(rnd.nextInt(5)))
                    .toPlace(toPlace.get(rnd.nextInt(5)))
                    .build();

            producer.sendMessage(parcel);
        }
    }

}
