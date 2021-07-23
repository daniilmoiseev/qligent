package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.repo.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BidToEntityConverter implements Converter<BidDto, Bid> {
    @Autowired
    private BidRepository bidRepository;

    @Override
    public Bid convert(BidDto source) {
        return bidRepository.findById(source.getId()).orElseThrow(NotFoundException::new);
    }
}
