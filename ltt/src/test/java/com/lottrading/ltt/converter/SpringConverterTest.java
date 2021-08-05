package com.lottrading.ltt.converter;

import com.lottrading.ltt.dao.BidDao;
import com.lottrading.ltt.dao.BuyoutDao;
import com.lottrading.ltt.dao.LotDao;
import com.lottrading.ltt.dao.UserDao;
import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.BuyoutDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Buyout;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.BidRepository;
import com.lottrading.ltt.repo.BuyoutRepository;
import com.lottrading.ltt.repo.LotRepository;
import com.lottrading.ltt.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class SpringConverterTest {

    @Autowired
    private BidDao bidDao;
    @Autowired
    private BidRepository bidRepo;

    @Autowired
    private BuyoutDao buyoutDao;
    @Autowired
    private BuyoutRepository buyoutRepo;

    @Autowired
    private LotDao lotDao;
    @Autowired
    private LotRepository lotRepo;

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRepository userRepo;

    @Test
    void convertBidToEntity() {
        BidToEntityConverter converter = new BidToEntityConverter(bidRepo);
        LotDto lotDto = LotDto.builder().id(1).build();
        UserDto userDto = UserDto.builder().id(1).build();

        BidDto bidDto = bidDao.saveBid(lotDto, userDto, 22);

        Bid entity = converter.convert(bidDto);

        assertEquals(22, entity.getBid());
    }

    @Test
    void convertBuyoutToEntity() {
        BuyoutToEntityConverter converter = new BuyoutToEntityConverter(buyoutRepo);
        LotDto lotDto = LotDto.builder().id(1).build();
        UserDto userDto = UserDto.builder().id(1).build();

        BuyoutDto buyoutDto = buyoutDao.saveBuyout(lotDto, userDto, 22);

        Buyout entity = converter.convert(buyoutDto);

        assertEquals(22, entity.getBuyout());
    }

    @Test
    void convertLotToEntity() {
        LotToEntityConverter converter = new LotToEntityConverter(lotRepo);
        lotDao.createLot("1", 100, 10);

        LotDto lotDto = LotDto.builder().id(1).title("1").buyout(100).archive(false).build();

        Lot entity = converter.convert(lotDto);

        assertNotNull(entity);
        assertEquals(100, entity.getBuyout());
        assertFalse(entity.isArchive());
    }

    @Test
    void convertUserToEntity() {
        UserToEntityConverter converter = new UserToEntityConverter(userRepo);
        userDao.createUser(500);

        UserDto userDto = UserDto.builder().id(1).cash(500).buyouts(new ArrayList<>()).build();

        User entity = converter.convert(userDto);

        assertNotNull(entity);
        assertEquals(500, entity.getCash());
    }

}
