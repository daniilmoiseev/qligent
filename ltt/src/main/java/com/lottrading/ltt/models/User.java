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

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "bid")
    private List<Bid> bids;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "buyout")
    private List<Buyout> buyouts;

    public User() {
    }

    public User(List<Bid> bids, List<Buyout> buyouts) {
        this.bids = bids;
        this.buyouts = buyouts;
    }
}
