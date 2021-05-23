package com.lottrading.ltt.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Builder
public class LotDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private int buyoutPrice;
    private int minBidPrice;
    private ArrayList<Integer> bidPrices;

}
