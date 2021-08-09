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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BidDaoTest {

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
    void saveBid() {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDto = userDao.createUser(500);

        BidDto bidDto = bidDao.saveBid(lotDto, userDto, 100);

        assertNotNull(bidDto);
        assertEquals(lotDto.getId(), bidDto.getLotId());
        assertEquals(userDto.getId(), bidDto.getUserId());
        assertEquals(100, bidDto.getBid());
    }

    @Test
    void findByLotId() {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDto = userDao.createUser(500);

        bidDao.saveBid(lotDto, userDto, 30);
        bidDao.saveBid(lotDto, userDto, 50);
        List<BidDto> bidDtoFromDbList = bidDao.findByLotId(lotDto.getId());

        assertTrue(CoreMatchers.is(bidDtoFromDbList.get(0).getBid()).matches(30));
        assertTrue(CoreMatchers.is(bidDtoFromDbList.get(1).getBid()).matches(50));
    }

    @Test
    void findByUserId() {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDto = userDao.createUser(500);

        bidDao.saveBid(lotDto, userDto, 70);
        bidDao.saveBid(lotDto, userDto, 100);
        List<BidDto> bidDtoFromDbList = bidDao.findByUserId(userDto.getId());

        assertTrue(CoreMatchers.is(bidDtoFromDbList.get(0).getBid()).matches(70));
        assertTrue(CoreMatchers.is(bidDtoFromDbList.get(1).getBid()).matches(100));
    }
}