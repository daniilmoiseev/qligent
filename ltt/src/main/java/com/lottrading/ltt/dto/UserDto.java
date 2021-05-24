package com.lottrading.ltt.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserDto {
    private long id;
    private List<Map<String, String>> lotBids;
    private List<Map<String, String>> buyLots;

    public UserDto(){}
}
