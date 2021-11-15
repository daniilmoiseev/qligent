package com.lottrading.ltt.dao;

import com.lottrading.ltt.dto.BidDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LotDaoTest {

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
    void createLot() {
        LotDto lotDto = lotDao.createLot("1", 1, 1);

        assertNotNull(lotDto);
        assertNotEquals(0, lotDto.getBuyout());
        assertFalse(CoreMatchers.is(lotDto.getMinBid()).matches(0));
        assertNotNull(lotDto.getBids());
        assertFalse(lotDto.isArchive());
    }

    @Test
    void offerBid() {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDto = userDao.createUser(500);

        LotDto lotDtoWithBid = lotDao.offerBid(lotDto.getId(), userDto.getId(), 100);

        assertNotNull(lotDtoWithBid.getBids());
        assertTrue(CoreMatchers.is(lotDtoWithBid.getBids().get(0).getBid()).matches(100));
        assertTrue(CoreMatchers.is(lotDtoWithBid.getBids().get(0).getLotId()).matches(lotDto.getId()));
        assertTrue(CoreMatchers.is(lotDtoWithBid.getBids().get(0).getUserId()).matches(userDto.getId()));
    }

    @Test
    void deleteLot() {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDto = userDao.createUser(500);

        LotDto deletedLot = lotDao.deleteLot(lotDto.getId(), userDto.getId(), lotDto.getBuyout());

        assertNotNull(deletedLot);
        assertTrue(deletedLot.isArchive());
    }

    @Test
    void getOneLot() {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDto = userDao.createUser(500);

        BidDto bidDto = bidDao.saveBid(lotDto, userDto, 20);

        LotDto lotDtoExtended = lotDao.getOneLot(lotDto.getId());

        assertNotNull(lotDtoExtended);
        assertTrue(CoreMatchers.is(lotDtoExtended.getBids().get(0).getBid()).matches(20));
        assertTrue(CoreMatchers.is(lotDtoExtended.getBids().get(0).getLotId()).matches(lotDto.getId()));
        assertTrue(CoreMatchers.is(lotDtoExtended.getBids().get(0).getUserId()).matches(userDto.getId()));

        assertNotNull(bidDto);
        assertTrue(CoreMatchers.is(bidDto.getLotId()).matches(lotDto.getId()));
        assertTrue(CoreMatchers.is(bidDto.getUserId()).matches(userDto.getId()));
    }

    @Test
    void findAll() {
        LotDto lotDtoFirst = lotDao.createLot("1", 1, 1);
        LotDto lotDtoSecond = lotDao.createLot("2", 2, 2);
        UserDto userDto = userDao.createUser(500);

        BidDto bidDto = bidDao.saveBid(lotDtoSecond, userDto, 20);

        List<LotDto> lotDtoList = lotDao.findAll();

        assertNotNull(lotDtoList);
        assertEquals(new ArrayList<>(), lotDtoList.get(0).getBids());
        assertTrue(CoreMatchers.is(lotDtoList.get(0).getBuyout()).matches(1));
        assertTrue(CoreMatchers.is(lotDtoList.get(1).getBuyout()).matches(2));

        assertNotNull(bidDto);
        assertTrue(CoreMatchers.is(bidDto.getLotId()).matches(lotDtoSecond.getId()));
        assertTrue(CoreMatchers.is(bidDto.getUserId()).matches(userDto.getId()));
        assertTrue(CoreMatchers.is(lotDtoList.get(1).getBids().get(0).getBid()).matches(20));
    }
}