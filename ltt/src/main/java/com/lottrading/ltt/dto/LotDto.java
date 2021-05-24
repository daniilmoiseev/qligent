package com.lottrading.ltt.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LotDto {
    private long id;
    private String title;
    private int buyoutPrice;
    private int minBidPrice;
    private int buyoutTime;
    private List<Map<String, String>> bidPrices;

    public LotDto(){}
}
