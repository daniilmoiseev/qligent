package com.lottrading.ltt.dao;

import com.lottrading.ltt.converter.BuyoutToDtoConverter;
import com.lottrading.ltt.converter.LotToEntityConverter;
import com.lottrading.ltt.converter.UserToEntityConverter;
import com.lottrading.ltt.dto.BuyoutDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.models.Buyout;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.BuyoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BuyoutDao {
    @Autowired
    private final BuyoutRepository buyoutRepository;

    @Autowired
    private final BuyoutToDtoConverter buyoutToDtoConverter;

    @Autowired
    private final UserToEntityConverter userToEntityConverter;

    @Autowired
    private final LotToEntityConverter lotToEntityConverter;

    public BuyoutDao(BuyoutRepository buyoutRepository, BuyoutToDtoConverter buyoutToDtoConverter, UserToEntityConverter userToEntityConverter, LotToEntityConverter lotToEntityConverter) {
        this.buyoutRepository = buyoutRepository;
        this.buyoutToDtoConverter = buyoutToDtoConverter;
        this.userToEntityConverter = userToEntityConverter;
        this.lotToEntityConverter = lotToEntityConverter;
    }

    public BuyoutDto saveBuyout(LotDto lotDto, UserDto userDto, int yBuyout) {
//        Lot lot = lotToEntityConverter.convert(lotDto);
//        User user = userToEntityConverter.convert(userDto);
        Buyout buyout = new Buyout(yBuyout);
//        buyout.setLot(lot);
//        buyout.setUser(user);
        buyout.setLotId(lotDto.getId());
        buyout.setUserId(userDto.getId());
        buyout.setZonedDateTime(ZonedDateTime.now());
        buyoutRepository.saveAndFlush(buyout);
        return buyoutToDtoConverter.convert(buyout);
    }

    @Transactional(readOnly = true)
    public List<BuyoutDto> findByUserId(long userId) {
        List<Buyout> buyoutList = buyoutRepository.findByUserId(userId);
        List<BuyoutDto> buyoutDtoList = new ArrayList<>();
        buyoutList.forEach(it -> {
            BuyoutDto buyoutDto = buyoutToDtoConverter.convert(it);
            buyoutDtoList.add(buyoutDto);
        });
        return buyoutDtoList;
    }
}
