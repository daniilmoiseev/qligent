package com.complexas.controller;

import com.complexas.CompleteDto;
import com.complexas.EnrichDto;
import com.complexas.ParcelDto;
import com.complexas.dto.AllDto;
import com.complexas.service.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Deprecated(since = "Augmentation process reworked")
@RestController
public class AugController {

    private final Producer producer;
    List<CompleteDto> completeDtoList = new ArrayList<>();
    List<ParcelDto> parcelDtoList = new ArrayList<>();

    public AugController(Producer producer) {
        this.producer = producer;
    }

    @GetMapping("/enrichParcel")
    public void enrichment() {
        List<ParcelDto> parcelFromKafka = AllDto.parcelFromKafka;
        List<EnrichDto> enrichFromKafka = AllDto.enrichFromKafka;

        for (ParcelDto parcelDto : parcelFromKafka) {
            boolean flag = false;
            for (EnrichDto enrichDto : enrichFromKafka) {
                if (parcelDto.getType().equals(enrichDto.getType()) &&
                    parcelDto.getWeight() <= enrichDto.getMaxWeight()) {

                    CompleteDto completeDto = CompleteDto.builder()
                            .id(parcelDto.getId())
                            .type(parcelDto.getType())
                            .dateOfSending(parcelDto.getDateOfSending())
                            .fromPlace(parcelDto.getFromPlace())
                            .toPlace(parcelDto.getToPlace())
                            .name(parcelDto.getName())
                            .weight(parcelDto.getWeight())
                            .maxWeight(enrichDto.getMaxWeight())
                            .typeOfDelivery(enrichDto.getTypeOfDelivery())
                            .volume(enrichDto.getVolume())
                            .build();

                    completeDtoList.add(completeDto);

                    flag = true;
                    break;
                }
            }
            if (!flag)
                parcelDtoList.add(parcelDto);
        }
    }

    @PostMapping("/pushToKafka")
    public void pushToKafka() {
        parcelDtoList.forEach(producer::sendParcel);
        completeDtoList.forEach(producer::sendComplete);
    }
}
