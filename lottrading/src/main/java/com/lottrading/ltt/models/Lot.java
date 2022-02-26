package com.lottrading.ltt.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "lot")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column
    private String title;

    @Column
    private int buyout;

    @Column
    private int minBid;

    @Column
    private int buyoutTime;

    @Column(nullable = false)
    private boolean archive;

    @OneToMany(mappedBy = "bid")
    private List<Bid> bids;

    public Lot() {
    }

    public Lot(String title, int buyout, int minBid, int buyoutTime, boolean archive, List<Bid> bids) {
        this.title = title;
        this.buyout = buyout;
        this.minBid = minBid;
        this.buyoutTime = buyoutTime;
        this.archive = archive;
        this.bids = bids;
    }
}