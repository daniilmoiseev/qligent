package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.LotBid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotBidRepository extends JpaRepository<LotBid, Long> {
}
