package com.lottrading.ltt.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column
    private int cash;

    @OneToMany(mappedBy = "bid")
    private List<Bid> bids;

    @OneToMany(mappedBy = "buyout")
    private List<Buyout> buyouts;

    public User() {
    }

    public User(int cash, List<Bid> bids, List<Buyout> buyouts) {
        this.cash = cash;
        this.bids = bids;
        this.buyouts = buyouts;
    }
}
