package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToEntityConverter implements Converter<UserDto, User> {

    private UserRepository userRepository;

    public UserToEntityConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User convert(UserDto source) {
        User user = userRepository.findById(source.getId()).orElseThrow(NotFoundException::new);
        user.setCash(source.getCash());
        return user;
    }
}