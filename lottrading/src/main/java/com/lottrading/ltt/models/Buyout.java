package com.lottrading.ltt.models;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "buyout")
public class Buyout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column
    private int buyout;

    @Column(nullable = false)
    private ZonedDateTime zonedDateTime;

    @Column
    private long lotId;

    @Column
    private long userId;

    public Buyout() {
    }

    public Buyout(int buyout) {
        this.buyout = buyout;
    }
}
