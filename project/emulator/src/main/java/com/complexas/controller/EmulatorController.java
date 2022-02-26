package com.complexas.controller;

import com.complexas.ContainerDto;
import com.complexas.ParcelDto;
import com.complexas.service.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Deprecated(since = "Emulation process reworked")
@RestController
public class EmulatorController {

    private final Producer producer;

    public EmulatorController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/emulate")
    public ContainerDto startEmulator() {
        emulation();
        List<ParcelDto> parcels = containerDto.getParcels();
        parcels.forEach(producer::sendMessage);

        return containerDto;
    }

    @GetMapping("/container")
    public ContainerDto getContainer() {
        return containerDto;
    }

    private final List<String> types = List.of("Box", "Package", "Letter", "Telegram");
    private final List<String> names = List.of("Car", "Phone", "Toys", "Photo", "Laptop");
    private final List<String> fromPlace = List.of("Rom", "Moscow", "St.Petersburg", "Nizhniy Novgorod", "Samara");
    private final List<String> toPlace = List.of("Vladivostok", "Sarov", "Rostov", "Sochi", "Vladimir");

    private ContainerDto containerDto;

    private void emulation() {
        Random rnd = new Random();
        ContainerDto container = ContainerDto.builder()
                .id(rnd.nextInt(1000))
                .countContainers(rnd.nextInt(7) + 10)
                .period(rnd.nextInt(5))
                .dateOfEmulation(ZonedDateTime.now())
                .build();

        List<ParcelDto> parcels = new ArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(container.getCountContainers());

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(()->{
            ParcelDto parcel = ParcelDto.builder()
                    .id(rnd.nextInt(1000))
                    .name(names.get(rnd.nextInt(5)))
                    .type(types.get(rnd.nextInt(4)))
                    .dateOfSending(ZonedDateTime.now())
                    .weight(rnd.nextInt(240) + 10)
                    .fromPlace(fromPlace.get(rnd.nextInt(5)))
                    .toPlace(toPlace.get(rnd.nextInt(5)))
                    .build();
            parcels.add(parcel);
            countDownLatch.countDown();
        }, 0, container.getPeriod(), TimeUnit.SECONDS);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        container.setParcels(parcels);

        containerDto = container;
    }
}
