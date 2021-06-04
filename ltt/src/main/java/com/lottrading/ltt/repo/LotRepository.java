package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotRepository extends JpaRepository<Lot, Long> {
}
