package com.lottrading.ltt.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Entity
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private int buyoutPrice;
    private int minBidPrice;
    private ArrayList<Integer> bidPrices;

    public Lot(String title, int buyoutPrice, int minBidPrice) {
        this.title = title;
        this.buyoutPrice = buyoutPrice;
        this.minBidPrice = minBidPrice;
    }
}
