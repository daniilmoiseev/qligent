package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findByArchiveIsFalse();
}
