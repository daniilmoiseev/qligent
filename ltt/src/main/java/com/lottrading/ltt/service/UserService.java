package com.lottrading.ltt.service;

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
    private LotService lotService;

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
        return userRepository.findAll();
    }

    public User getOneUser(long id) {
        return userRepository.getOne(id);
    }
}
