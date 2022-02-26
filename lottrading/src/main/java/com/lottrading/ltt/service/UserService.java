package com.lottrading.ltt.service;

import com.lottrading.ltt.dao.UserDao;
import com.lottrading.ltt.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDto createUser(int cash) {
        return userDao.createUser(cash);
    }

    public List<UserDto> findAll() {
        return userDao.findAll();
    }

    public UserDto getOneUser(long id) {
        return userDao.getOneUser(id);
    }
}
