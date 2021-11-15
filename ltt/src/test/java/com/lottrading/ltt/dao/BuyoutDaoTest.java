package com.lottrading.ltt.dao;

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
class BuyoutDaoTest {

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
    void saveBuyout() {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDto = userDao.createUser(500);

        BuyoutDto buyoutDto = buyoutDao.saveBuyout(lotDto, userDto, 100);

        assertNotNull(buyoutDto);
        assertEquals(lotDto.getId(), buyoutDto.getLotId());
        assertEquals(userDto.getId(), buyoutDto.getUserId());
        assertEquals(100, buyoutDto.getBuyout());
    }

    @Test
    void findByUserId() {
        LotDto lotDtoFirst = lotDao.createLot("1", 1, 1);
        LotDto lotDtoSecond = lotDao.createLot("2", 2, 2);
        UserDto userDto = userDao.createUser(500);

        buyoutDao.saveBuyout(lotDtoFirst, userDto, 70);
        buyoutDao.saveBuyout(lotDtoSecond, userDto, 100);
        List<BuyoutDto> buyoutDtoFromDbList = buyoutDao.findByUserId(userDto.getId());

        assertTrue(CoreMatchers.is(buyoutDtoFromDbList.get(0).getBuyout()).matches(70));
        assertTrue(CoreMatchers.is(buyoutDtoFromDbList.get(0).getLotId()).matches(lotDtoFirst.getId()));
        assertTrue(CoreMatchers.is(buyoutDtoFromDbList.get(1).getBuyout()).matches(100));
        assertTrue(CoreMatchers.is(buyoutDtoFromDbList.get(1).getLotId()).matches(lotDtoSecond.getId()));
    }
}