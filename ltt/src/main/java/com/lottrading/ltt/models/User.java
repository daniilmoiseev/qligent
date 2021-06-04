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
    @OneToMany(mappedBy = "userBid")
    private List<UserBid> userBids;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "userBuyout")
    private List<UserBuyout> userBuyouts;

    public User() {
    }

    public User(List<UserBid> userBids, List<UserBuyout> userBuyouts) {
        this.userBids = userBids;
        this.userBuyouts = userBuyouts;
    }
}
