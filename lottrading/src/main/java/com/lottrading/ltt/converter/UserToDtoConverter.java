package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.models.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserToDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return UserDto.builder()
                .id(source.getId())
                .cash(source.getCash())
                .bids(new ArrayList<>())
                .buyouts(new ArrayList<>())
                .build();
    }
}