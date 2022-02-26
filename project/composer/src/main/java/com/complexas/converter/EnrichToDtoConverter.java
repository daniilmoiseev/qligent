package com.complexas.converter;

import com.complexas.EnrichDto;
import com.complexas.model.Enrich;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EnrichToDtoConverter implements Converter<Enrich, EnrichDto> {

    @Override
    public EnrichDto convert(Enrich source) {
        return EnrichDto.builder()
                .id(source.getId())
                .type(source.getType())
                .maxWeight(source.getMaxWeight())
                .typeOfDelivery(source.getTypeOfDelivery())
                .volume(source.getVolume())
                .build();
    }
}
