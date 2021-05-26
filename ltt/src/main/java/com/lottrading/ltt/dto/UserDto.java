package com.lottrading.ltt.dto;

import com.lottrading.ltt.models.UserBid;
import com.lottrading.ltt.models.UserBuyout;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private long id;
    private List<UserBid> userBids;
    private List<UserBuyout> userBuyouts;
}
