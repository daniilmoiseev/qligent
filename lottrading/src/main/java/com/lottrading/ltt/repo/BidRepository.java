package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByLotId(long lotId);

    List<Bid> findByUserId(long userId);
}
