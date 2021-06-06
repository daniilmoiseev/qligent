package com.lottrading.ltt.service;

import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Buyout;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private BidService bidService;

    @Autowired
    private BuyoutService buyoutService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser() {
        User user = new User(new ArrayList<>(), new ArrayList<>());
        return userRepository.saveAndFlush(user);
    }

    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        users.forEach(it -> {
            List<Bid> bids = bidService.findByUserId(it.getId());
            List<Buyout> buyouts = buyoutService.findByUserId(it.getId());
            if(!bids.isEmpty()){
                it.setBids(bids);
            } else {
                it.setBids(new ArrayList<>());
            }
            if(!buyouts.isEmpty()){
                it.setBuyouts(buyouts);
            } else {
                it.setBuyouts(new ArrayList<>());
            }
        });
        return users;
    }

    public User getOneUser(long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        List<Bid> bids = bidService.findByUserId(user.getId());
        List<Buyout> buyouts = buyoutService.findByUserId(user.getId());
        if(!bids.isEmpty()){
            user.setBids(bids);
        } else {
            user.setBids(new ArrayList<>());
        }
        if(!buyouts.isEmpty()){
            user.setBuyouts(buyouts);
        } else {
            user.setBuyouts(new ArrayList<>());
        }
        return user;
    }
}
