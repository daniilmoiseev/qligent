package com.lottrading.ltt.dto;

import com.lottrading.ltt.models.LotBid;
import lombok.Data;

import java.util.List;

@Data
public class LotDto {
    private long id;
    private String title;
    private int buyout;
    private int minBid;
    private int buyoutTime;
    private boolean archive;
    private List<LotBid> lotBids;
}
