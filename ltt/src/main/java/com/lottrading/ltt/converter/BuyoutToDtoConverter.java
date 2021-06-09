package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.BuyoutDto;
import com.lottrading.ltt.models.Buyout;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BuyoutToDtoConverter implements Converter<Buyout, BuyoutDto> {

    @Override
    public BuyoutDto convert(Buyout source) {
        return BuyoutDto.builder()
                .id(source.getId())
                .buyout(source.getBuyout())
//                .lotId(source.getLot().getId())
//                .userId(source.getUser().getId())
                .lotId(source.getLotId())
                .userId(source.getUserId())
                .zonedDateTime(source.getZonedDateTime())
                .build();
    }
}
