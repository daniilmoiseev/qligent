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
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "title")
    private String title;
    @Column(name = "buyout")
    private int buyout;
    @Column(name = "minbid")
    private int minBid;
    @Column(name = "buyouttime")
    private int buyoutTime;
    @Column(name = "archive", nullable = false)
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