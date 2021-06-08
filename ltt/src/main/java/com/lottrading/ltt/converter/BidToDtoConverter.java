package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.models.Bid;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BidToDtoConverter implements Converter<Bid, BidDto> {

    @Override
    public BidDto convert(Bid source) {
        return BidDto.builder()
                .id(source.getId())
                .bid(source.getBid())
                .lotId(source.getLotId())
                .userId(source.getUserId())
                .zonedDateTime(source.getZonedDateTime())
                .build();
    }
}
