package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.BuyoutDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Buyout;
import com.lottrading.ltt.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToDtoConverter implements Converter<User, UserDto> {

    @Autowired
    private BidToDtoConverter bidToDtoConverter;

    @Autowired
    private BuyoutToDtoConverter buyoutToDtoConverter;

    @Override
    public UserDto convert(User source) {
        List<Bid> bidList = source.getBids();
        List<BidDto> bidDtoList = new ArrayList<>();
        bidList.forEach(it -> {
            BidDto bidDto = bidToDtoConverter.convert(it);
            bidDtoList.add(bidDto);
        });

        List<Buyout> buyoutList = source.getBuyouts();
        List<BuyoutDto> buyoutDtoList = new ArrayList<>();
        buyoutList.forEach(it -> {
            BuyoutDto buyoutDto = buyoutToDtoConverter.convert(it);
            buyoutDtoList.add(buyoutDto);
        });

        return UserDto.builder()
                .id(source.getId())
                .bids(bidDtoList)
                .buyouts(buyoutDtoList)
                .cash(source.getCash())
                .build();
    }
}