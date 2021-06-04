package com.lottrading.ltt.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "userbuyout")
public class UserBuyout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "lotId")
    private long lotId;

    @Column(name = "buyout")
    private int buyout;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private User userBuyout;

    public UserBuyout() {
    }

    public UserBuyout(long lotId, int buyout) {
        this.lotId = lotId;
        this.buyout = buyout;
    }
}
