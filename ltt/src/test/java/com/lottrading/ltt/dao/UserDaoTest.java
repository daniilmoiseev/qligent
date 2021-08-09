package com.lottrading.ltt.dao;

import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.BuyoutDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.repo.BidRepository;
import com.lottrading.ltt.repo.BuyoutRepository;
import com.lottrading.ltt.repo.LotRepository;
import com.lottrading.ltt.repo.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserDaoTest {

    @Autowired
    private LotDao lotDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BidDao bidDao;
    @Autowired
    private BuyoutDao buyoutDao;

    @Autowired
    private LotRepository lotRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BidRepository bidRepo;
    @Autowired
    private BuyoutRepository buyoutRepo;

    @Test
    void createUser() {
        UserDto userDto = userDao.createUser(500);

        assertNotNull(userDto);
        assertNotEquals(0, userDto.getCash());
        assertNotNull(userDto.getBids());
        assertNotNull(userDto.getBuyouts());
    }

    @Test
    void updateUser() {
        UserDto userDto = userDao.createUser(500);
        userDto.setCash(222);
        UserDto userDtoExtended = userDao.updateUser(userDto);

        assertNotNull(userDtoExtended);
        assertEquals(222, userDtoExtended.getCash());
    }

    @Test
    void findAll() {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDtoFirst = userDao.createUser(100);
        UserDto userDtoSecond = userDao.createUser(500);

        BidDto bidDtoFirst = bidDao.saveBid(lotDto, userDtoFirst, 70);
        BidDto bidDtoSecond = bidDao.saveBid(lotDto, userDtoSecond, 150);

        lotDao.deleteLot(lotDto.getId(), userDtoSecond.getId(), 150);
        List<UserDto> userDtoList = userDao.findAll();

        assertNotNull(userDtoList);
        assertTrue(CoreMatchers.is(userDtoList.get(0).getBids().get(0).getBid()).matches(70));
        assertTrue(CoreMatchers.is(userDtoList.get(1).getBids().get(0).getBid()).matches(150));
        assertTrue(CoreMatchers.is(userDtoList.get(0).getCash()).matches(100));
        assertTrue(CoreMatchers.is(userDtoList.get(1).getCash()).matches(350));
        assertTrue(CoreMatchers.is(userDtoList.get(1).getBuyouts().get(0).getBuyout()).matches(150));

        assertNotNull(bidDtoFirst);
        assertTrue(CoreMatchers.is(bidDtoFirst.getLotId()).matches(lotDto.getId()));
        assertTrue(CoreMatchers.is(bidDtoFirst.getUserId()).matches(userDtoFirst.getId()));
        assertTrue(CoreMatchers.is(bidDtoFirst.getBid()).matches(70));

        assertNotNull(bidDtoSecond);
        assertTrue(CoreMatchers.is(bidDtoSecond.getLotId()).matches(lotDto.getId()));
        assertTrue(CoreMatchers.is(bidDtoSecond.getUserId()).matches(userDtoSecond.getId()));
        assertTrue(CoreMatchers.is(bidDtoSecond.getBid()).matches(150));
    }

    @Test
    void getOneUser() {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDto = userDao.createUser(500);

        BidDto bidDto = bidDao.saveBid(lotDto, userDto, 30);
        BuyoutDto buyoutDto = buyoutDao.saveBuyout(lotDto, userDto, 30);

        UserDto userDtoExtended = userDao.getOneUser(userDto.getId());

        assertNotNull(userDtoExtended);
        assertTrue(CoreMatchers.is(userDtoExtended.getBids().get(0).getBid()).matches(30));
        assertTrue(CoreMatchers.is(userDtoExtended.getBids().get(0).getLotId()).matches(lotDto.getId()));
        assertTrue(CoreMatchers.is(userDtoExtended.getBids().get(0).getUserId()).matches(userDto.getId()));
        assertTrue(CoreMatchers.is(userDtoExtended.getBuyouts().get(0).getBuyout()).matches(30));
        assertTrue(CoreMatchers.is(userDtoExtended.getBuyouts().get(0).getLotId()).matches(lotDto.getId()));
        assertTrue(CoreMatchers.is(userDtoExtended.getBuyouts().get(0).getUserId()).matches(userDto.getId()));

        assertNotNull(bidDto);
        assertTrue(CoreMatchers.is(bidDto.getLotId()).matches(lotDto.getId()));
        assertTrue(CoreMatchers.is(bidDto.getUserId()).matches(userDto.getId()));

        assertNotNull(buyoutDto);
        assertTrue(CoreMatchers.is(buyoutDto.getLotId()).matches(lotDto.getId()));
        assertTrue(CoreMatchers.is(buyoutDto.getUserId()).matches(userDto.getId()));
    }
}