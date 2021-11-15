package com.lottrading.ltt.controller;

import com.lottrading.ltt.dao.LotDao;
import com.lottrading.ltt.dao.UserDao;
import com.lottrading.ltt.repo.LotRepository;
import com.lottrading.ltt.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LotsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LotDao lotDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private LotRepository lotRepo;
    @Autowired
    private UserRepository userRepo;

    @Test
    void findAllLots() throws Exception {
        lotDao.createLot("1", 111, 15);
        lotDao.createLot("2", 250, 20);
        this.mockMvc.perform(get("/lots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("lots[0].archive").value(false))
                .andExpect(jsonPath("lots[1].archive").value(false))
                .andExpect(jsonPath("lots[0].minBid").value(15))
                .andExpect(jsonPath("lots[1].buyout").value(250))
                .andExpect(jsonPath("lots[0].bids", hasSize(0)));
    }

    @Test
    void getOneLot() throws Exception {
        lotDao.createLot("1", 1, 1);
        this.mockMvc.perform(get("/lots/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("archive").value(false))
                .andExpect(jsonPath("bids").isArray())
                .andExpect(jsonPath("bids", hasSize(0)));
    }

    @Test
    void createLot() throws Exception {
        this.mockMvc.perform(post("/lots").param("title", "1").param("buyout", "1").param("minBid", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("archive").value(false))
                .andExpect(jsonPath("bids").isArray())
                .andExpect(jsonPath("bids", hasSize(0)));
    }

    @Test
    void findAllUsers() throws Exception {
        userDao.createUser(100);
        userDao.createUser(300);
        userDao.createUser(500);
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("users[0].cash").value(100))
                .andExpect(jsonPath("users[1].cash").value(300))
                .andExpect(jsonPath("users[2].cash").value(500));
    }

    @Test
    void getOneUser() throws Exception {
        userDao.createUser(350);
        this.mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("bids").isArray())
                .andExpect(jsonPath("bids", hasSize(0)))
                .andExpect(jsonPath("cash").value(350));
    }

    @Test
    void createUser() throws Exception {
        this.mockMvc.perform(post("/users").param("cash", "222"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("bids", hasSize(0)))
                .andExpect(jsonPath("buyouts", hasSize(0)))
                .andExpect(jsonPath("cash").value(222));
    }

    @Test
    void offerBid() throws Exception {
        lotDao.createLot("1", 1, 1);
        userDao.createUser(100);
        this.mockMvc.perform(put("/lots").param("id", "1").param("userId", "1").param("yourBid", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("bids[0].bid").value(10));
    }

    @Test
    void deleteLot() throws Exception {
        lotDao.createLot("1", 1, 1);
        userDao.createUser(100);
        this.mockMvc.perform(delete("/lots/1").param("id", "1").param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}