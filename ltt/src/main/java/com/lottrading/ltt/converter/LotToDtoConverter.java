package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Lot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LotToDtoConverter implements Converter<Lot, LotDto> {

    @Autowired
    private BidToDtoConverter bidToDtoConverter;

    @Override
    public LotDto convert(Lot source) {
        List<Bid> bidList = source.getBids();
        List<BidDto> bidDtoList = new ArrayList<>();
        bidList.forEach(it -> {
            BidDto bidDto = bidToDtoConverter.convert(it);
            bidDtoList.add(bidDto);
        });

        return LotDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .buyout(source.getBuyout())
                .minBid(source.getMinBid())
                .buyoutTime(source.getBuyoutTime())
                .archive(source.isArchive())
                .bids(bidDtoList)
                .build();
    }
}
