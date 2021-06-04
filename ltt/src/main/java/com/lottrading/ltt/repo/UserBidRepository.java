package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.UserBid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBidRepository extends JpaRepository<UserBid, Long> {
}
