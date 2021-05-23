package com.lottrading.ltt.dto;

import lombok.Data;

import java.util.List;

@Data
public class LotDto {
    private long id;
    private String title;
    private int buyoutPrice;
    private int minBidPrice;
    private List<Integer> bidPrices;

    public LotDto(){}
}
