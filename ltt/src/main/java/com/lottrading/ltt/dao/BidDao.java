package com.lottrading.ltt.dao;

import com.lottrading.ltt.converter.BidToDtoConverter;
import com.lottrading.ltt.converter.LotToEntityConverter;
import com.lottrading.ltt.converter.UserToEntityConverter;
import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BidDao {
    @Autowired
    private final BidRepository bidRepository;

    @Autowired
    private final BidToDtoConverter bidToDtoConverter;

    @Autowired
    private final UserToEntityConverter userToEntityConverter;

    @Autowired
    private final LotToEntityConverter lotToEntityConverter;

    public BidDao(BidRepository bidRepository, BidToDtoConverter bidToDtoConverter, UserToEntityConverter userToEntityConverter, LotToEntityConverter lotToEntityConverter) {
        this.bidRepository = bidRepository;
        this.bidToDtoConverter = bidToDtoConverter;
        this.userToEntityConverter = userToEntityConverter;
        this.lotToEntityConverter = lotToEntityConverter;
    }

    public BidDto saveBid(LotDto lotDto, UserDto userDto, int yBid) {
        Lot lot = lotToEntityConverter.convert(lotDto);
        User user = userToEntityConverter.convert(userDto);
        Bid bid = new Bid(yBid);
        bid.setLotId(lot.getId());
        bid.setUserId(user.getId());
        bid.setZonedDateTime(ZonedDateTime.now());
        bidRepository.saveAndFlush(bid);
        return bidToDtoConverter.convert(bid);
    }

    public List<BidDto> findByLotId(long lotId) {
        List<Bid> bidList = bidRepository.findByLotId(lotId);
        List<BidDto> bidDtoList = new ArrayList<>();
        bidList.forEach(it -> {
            BidDto bidDto = bidToDtoConverter.convert(it);
            bidDtoList.add(bidDto);
        });
        return bidDtoList;
    }

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
