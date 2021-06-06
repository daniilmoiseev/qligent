package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.Buyout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyoutRepository extends JpaRepository<Buyout, Long> {
    List<Buyout> findByUserId(long userId);
}
