package com.lottrading.ltt.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "userbid")
public class UserBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "lotId")
    private long lotId;

    @Column(name = "bid")
    private int bid;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private User userBid;

    public UserBid() {
    }

    public UserBid(long lotId, int bid) {
        this.lotId = lotId;
        this.bid = bid;
    }
}
