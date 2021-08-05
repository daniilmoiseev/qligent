package com.lottrading.ltt.controller;

import com.lottrading.ltt.dao.BidDao;
import com.lottrading.ltt.dao.BuyoutDao;
import com.lottrading.ltt.dao.LotDao;
import com.lottrading.ltt.dao.UserDao;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.repo.BidRepository;
import com.lottrading.ltt.repo.BuyoutRepository;
import com.lottrading.ltt.repo.LotRepository;
import com.lottrading.ltt.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders. *;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class LotsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LotDao lotDao;
    @Autowired
    private UserDao userDao;

    @Test
    void findAllLots() throws Exception {
        this.mockMvc.perform(get("/lots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    void getOneLot() throws Exception {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        String lotId = Long.toString(lotDto.getId());
        this.mockMvc.perform(get("/lots/"+lotId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("\"bids\":[]")))
                .andExpect(content().string(containsString("\"archive\":false")));
    }

    @Test
    void createLot() throws Exception {
        this.mockMvc.perform(post("/lots").param("title", "1").param("buyout", "1").param("minBid", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("\"bids\":[]")))
                .andExpect(content().string(containsString("\"archive\":false")));
    }

    @Test
    void findAllUsers() throws Exception {
        userDao.createUser(100);
        userDao.createUser(300);
        userDao.createUser(500);
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("\"cash\":100")))
                .andExpect(content().string(containsString("\"cash\":300")))
                .andExpect(content().string(containsString("\"cash\":500")));
    }

    @Test
    void getOneUser() throws Exception {
        UserDto userDto = userDao.createUser(350);
        String id = Long.toString(userDto.getId());
        this.mockMvc.perform(get("/users/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("\"bids\":[]")))
                .andExpect(content().string(containsString("\"cash\":350")));
    }

    @Test
    void createUser() throws Exception {
        this.mockMvc.perform(post("/users").param("cash", "222"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("\"bids\":[]")))
                .andExpect(content().string(containsString("\"buyouts\":[]")))
                .andExpect(content().string(containsString("\"cash\":222")));
    }

    @Test
    void offerBid() throws Exception {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDto = userDao.createUser(100);
        String lotId = Long.toString(lotDto.getId());
        String userId = Long.toString(userDto.getId());
        this.mockMvc.perform(put("/lots").param("id", lotId).param("userId", userId).param("yourBid", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("\"bid\":10")));
    }

    @Test
    void deleteLot() throws Exception {
        LotDto lotDto = lotDao.createLot("1", 1, 1);
        UserDto userDto = userDao.createUser(100);
        String lotId = Long.toString(lotDto.getId());
        String userId = Long.toString(userDto.getId());
        this.mockMvc.perform(delete("/lots/1").param("id", lotId).param("userId", userId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}