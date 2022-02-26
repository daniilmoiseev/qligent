package com.lottrading.ltt.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LotDto {
    private long id;
    private String title;
    private int buyout;
    private int minBid;
    private int buyoutTime;
    private boolean archive;
    private List<BidDto> bids;
}
