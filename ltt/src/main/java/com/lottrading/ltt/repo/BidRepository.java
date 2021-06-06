package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
//    @Query(value = "select * from bid where lotId = '${lotId}'", nativeQuery = true)
    List<Bid> findByLotId(long lotId);

//    @Query(value = "select * from bid where userId = '${userId}'", nativeQuery = true)
    List<Bid> findByUserId(long userId);
}
