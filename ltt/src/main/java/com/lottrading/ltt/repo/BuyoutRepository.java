package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.Buyout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyoutRepository extends JpaRepository<Buyout, Long> {
//    @Query(value = "select * from buyout where userId = '${userId}'", nativeQuery = true)
    List<Buyout> findByUserId(long userId);
}
