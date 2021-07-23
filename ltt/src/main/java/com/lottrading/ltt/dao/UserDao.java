package com.lottrading.ltt.dao;

import com.lottrading.ltt.converter.UserToDtoConverter;
import com.lottrading.ltt.converter.UserToEntityConverter;
import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.BuyoutDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BidDao bidDao;

    @Autowired
    private final BuyoutDao buyoutDao;

    @Autowired
    private final UserToDtoConverter userToDtoConverter;

    @Autowired
    private final UserToEntityConverter userToEntityConverter;

    public UserDao(UserRepository userRepository, BidDao bidDao, BuyoutDao buyoutDao, UserToDtoConverter userToDtoConverter, UserToEntityConverter userToEntityConverter) {
        this.userRepository = userRepository;
        this.bidDao = bidDao;
        this.buyoutDao = buyoutDao;
        this.userToDtoConverter = userToDtoConverter;
        this.userToEntityConverter = userToEntityConverter;
    }

    public UserDto createUser(int cash) {
        User user = new User(cash, new ArrayList<>(), new ArrayList<>());
        userRepository.saveAndFlush(user);
        return userToDtoConverter.convert(user);
    }

    public UserDto updateUser(UserDto userDto) {
        User user = userToEntityConverter.convert(userDto);
        userRepository.saveAndFlush(user);
        return userDto;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        users.forEach(it -> {
            UserDto userDto = userToDtoConverter.convert(it);
            userDtoList.add(userDto);
        });

        userDtoList.forEach(it -> {
            List<BidDto> bids = bidDao.findByUserId(it.getId());
            List<BuyoutDto> buyouts = buyoutDao.findByUserId(it.getId());
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
        return userDtoList;
    }

    @Transactional(readOnly = true)
    public UserDto getOneUser(long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        UserDto userDto = userToDtoConverter.convert(user);
        List<BidDto> bids = bidDao.findByUserId(user.getId());
        List<BuyoutDto> buyouts = buyoutDao.findByUserId(user.getId());
        if(!bids.isEmpty()){
            userDto.setBids(bids);
        } else {
            userDto.setBids(new ArrayList<>());
        }
        if(!buyouts.isEmpty()){
            userDto.setBuyouts(buyouts);
        } else {
            userDto.setBuyouts(new ArrayList<>());
        }
        return userDto;
    }
}
