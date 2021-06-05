package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    @Query(value = "select * from bid where lotId = '${lot}'", nativeQuery = true)
    List<Bid> findByLot(Lot lot);

    @Query(value = "select * from bid where userId = '${user}'", nativeQuery = true)
    List<Bid> findByUser(User user);
}
