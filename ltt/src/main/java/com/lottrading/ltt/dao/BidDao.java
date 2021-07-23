package com.lottrading.ltt.dao;

import com.lottrading.ltt.converter.BidToDtoConverter;
import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.repo.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BidDao {
    @Autowired
    private final BidRepository bidRepository;

    @Autowired
    private final BidToDtoConverter bidToDtoConverter;

    public BidDao(BidRepository bidRepository, BidToDtoConverter bidToDtoConverter) {
        this.bidRepository = bidRepository;
        this.bidToDtoConverter = bidToDtoConverter;
    }

    public BidDto saveBid(LotDto lotDto, UserDto userDto, int yBid) {
        Bid bid = new Bid(yBid);
        bid.setLotId(lotDto.getId());
        bid.setUserId(userDto.getId());
        bid.setZonedDateTime(ZonedDateTime.now());
        bidRepository.saveAndFlush(bid);
        return bidToDtoConverter.convert(bid);
    }

    @Transactional(readOnly = true)
    public List<BidDto> findByLotId(long lotId) {
        List<Bid> bidList = bidRepository.findByLotId(lotId);
        List<BidDto> bidDtoList = new ArrayList<>();
        bidList.forEach(it -> {
            BidDto bidDto = bidToDtoConverter.convert(it);
            bidDtoList.add(bidDto);
        });
        return bidDtoList;
    }

    @Transactional(readOnly = true)
    public List<BidDto> findByUserId(long userId) {
        List<Bid> bidList = bidRepository.findByUserId(userId);
        List<BidDto> bidDtoList = new ArrayList<>();
        bidList.forEach(it -> {
            BidDto bidDto = bidToDtoConverter.convert(it);
            bidDtoList.add(bidDto);
        });
        return bidDtoList;
    }
}
