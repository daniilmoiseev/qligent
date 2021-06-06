package com.lottrading.ltt.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "lot_id")
//    private Lot lot;
//
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "users_id")
//    private User user;

    @JoinColumn(name = "lot_id")
    private long lotId;

    @JoinColumn(name = "users_id")
    private long userId;

    public Bid() {
    }

    public Bid(int bid) {
        this.bid = bid;
    }
}
