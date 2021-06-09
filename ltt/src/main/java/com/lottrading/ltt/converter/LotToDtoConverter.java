package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.models.Lot;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LotToDtoConverter implements Converter<Lot, LotDto> {
    @Override
    public LotDto convert(Lot source) {
        return LotDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .buyout(source.getBuyout())
                .minBid(source.getMinBid())
                .buyoutTime(source.getBuyoutTime())
                .archive(source.isArchive())
                .bids(new ArrayList<>())
                .build();
    }
}