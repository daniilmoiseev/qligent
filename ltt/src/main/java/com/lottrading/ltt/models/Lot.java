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
    private int buyoutTime;
    @Column(name = "archive", nullable = false)
    private boolean archive;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lot")
    private List<LotBid> lotBids;

    public Lot() {
    }

    public Lot(String title, int buyout, int minBid, boolean archive, List<LotBid> lotBids) {
        this.title = title;
        this.buyout = buyout;
        this.minBid = minBid;
        this.archive = archive;
        this.lotBids = lotBids;
    }
}