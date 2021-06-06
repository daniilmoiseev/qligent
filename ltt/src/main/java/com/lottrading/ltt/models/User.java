package com.lottrading.ltt.models;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "cash")
    private int cash;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "bid")
    private List<Bid> bids;

    @LazyCollection(LazyCollectionOption.FALSE)
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
