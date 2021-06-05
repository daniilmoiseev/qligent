package com.lottrading.ltt.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "bid")
    private int bid;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "lot_id")
//    private Lot lot;
//
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "users_id")
//    private User user;

    @Column(name = "lotId")
    private long lotId;

    @Column(name = "userId")
    private long userId;

    public Bid() {
    }

    public Bid(int bid) {
        this.bid = bid;
    }
}
