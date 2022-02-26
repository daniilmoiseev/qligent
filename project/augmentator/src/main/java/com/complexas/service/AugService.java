package com.complexas.service;

import com.complexas.CompleteDto;
import com.complexas.EnrichDto;
import com.complexas.ParcelDto;
import com.complexas.store.EnrichmentStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AugService {

    private final EnrichmentStore enrichmentStore;
    private final Producer producer;

    @Autowired
    public AugService(EnrichmentStore enrichmentStore, Producer producer) {
        this.enrichmentStore = enrichmentStore;
        this.producer = producer;
    }

    public void enrich(ParcelDto parcelDto) {
        EnrichDto enrichDto = enrichmentStore.get(parcelDto.getType());

        if (enrichDto != null && parcelDto.getWeight() <= enrichDto.getMaxWeight()) {
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

            producer.sendComplete(completeDto);
        } else {
            producer.sendParcel(parcelDto);
        }
    }
}
