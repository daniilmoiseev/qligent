package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LotToEntityConverter implements Converter<LotDto, Lot> {
    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private BidToEntityConverter bidToEntityConverter;

    @Override
    public Lot convert(LotDto source) {
        Lot lot = lotRepository.findById(source.getId()).orElseThrow(NotFoundException::new);
        List<BidDto> bidDtoList = source.getBids();
        List<Bid> bidList = new ArrayList<>();
        bidDtoList.forEach(it -> {
            Bid bid = bidToEntityConverter.convert(it);
            bidList.add(bid);
        });
        lot.setBuyout(source.getBuyout());
        lot.setBuyoutTime(source.getBuyoutTime());
        lot.setTitle(source.getTitle());
        lot.setMinBid(source.getMinBid());
        lot.setArchive(source.isArchive());
        lot.setBids(bidList);
        return lot;
    }
}
