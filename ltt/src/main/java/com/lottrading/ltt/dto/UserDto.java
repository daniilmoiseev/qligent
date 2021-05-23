package com.lottrading.ltt.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private long id;
    private List<Integer> lotBids;

    public UserDto(){}
}
