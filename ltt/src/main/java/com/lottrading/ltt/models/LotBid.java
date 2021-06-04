package com.lottrading.ltt.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lotbid")
public class LotBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "userId")
    private long userId;

    @Column(name = "bid")
    private int bid;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    public LotBid() {
    }

    public LotBid(long userId, int bid) {
        this.userId = userId;
        this.bid = bid;
    }
}
