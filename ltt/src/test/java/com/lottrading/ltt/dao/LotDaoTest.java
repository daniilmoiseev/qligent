package com.lottrading.ltt.dao;

import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.LotRepository;
import com.lottrading.ltt.repo.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LotDaoTest {

    @Autowired
    private LotDao lotDao;

    @Autowired
    private UserDao userDao;

    @MockBean
    private LotRepository lotRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private BidDao bidDao;

    @Test
    void createLot() {
        Lot lot = new Lot("1",1,1,60,false, new ArrayList<>());
        LotDto lotDto = lotDao.createLot(lot.getTitle(), lot.getBuyout(), lot.getMinBid());

        assertNotNull(lotDto);
        assertNotEquals(0, lotDto.getBuyout());
        assertFalse(CoreMatchers.is(lotDto.getMinBid()).matches(0));
        assertNotNull(lotDto.getBids());
        assertFalse(lotDto.isArchive());

        Mockito.verify(lotRepo, Mockito.times(1)).saveAndFlush(lot);
    }

    @Test
    void offerBid() {
        Lot lot = new Lot("1",1,1,60,false, new ArrayList<>());
        LotDto lotDto = lotDao.createLot(lot.getTitle(), lot.getBuyout(), lot.getMinBid());
        lot.setId(lotDto.getId());

        User user = new User(500, new ArrayList<>(), new ArrayList<>());
        UserDto userDto = userDao.createUser(user.getCash());

//        Mockito.doReturn(lotDto).when(lotDaoMock).getOneLot(lotDto.getId());

        LotDto lotDtoWithBid = lotDao.offerBid(lotDto.getId(), userDto.getId(), 100);

        assertNotNull(lotDtoWithBid.getBids());
        assertTrue(CoreMatchers.is(lotDtoWithBid.getBids().get(0).getBid()).matches(100));
        assertTrue(CoreMatchers.is(lotDtoWithBid.getBids().get(0).getLotId()).matches(lotDto.getId()));
        assertTrue(CoreMatchers.is(lotDtoWithBid.getBids().get(0).getUserId()).matches(userDto.getId()));
    }
}