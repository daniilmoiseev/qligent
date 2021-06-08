package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.BuyoutDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Buyout;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToEntityConverter implements Converter<UserDto, User> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BidToEntityConverter bidToEntityConverter;

    @Autowired
    private BuyoutToEntityConverter buyoutToEntityConverter;

    @Override
    public User convert(UserDto source) {
        User user = userRepository.findById(source.getId()).orElseThrow(NotFoundException::new);
        List<BidDto> bidDtoList = source.getBids();
        List<Bid> bidList = new ArrayList<>();
        bidDtoList.forEach(it -> {
            Bid bid = bidToEntityConverter.convert(it);
            bidList.add(bid);
        });

        List<BuyoutDto> buyoutDtoList = source.getBuyouts();
        List<Buyout> buyoutList = new ArrayList<>();
        buyoutDtoList.forEach(it -> {
            Buyout buyout = buyoutToEntityConverter.convert(it);
            buyoutList.add(buyout);
        });

        user.setCash(source.getCash());
        user.setBids(bidList);
        user.setBuyouts(buyoutList);
        return user;
    }
}
