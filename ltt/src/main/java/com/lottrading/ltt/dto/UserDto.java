package com.lottrading.ltt.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private long id;
    private int cash;
    private List<BidDto> bids;
    private List<BuyoutDto> buyouts;
}
