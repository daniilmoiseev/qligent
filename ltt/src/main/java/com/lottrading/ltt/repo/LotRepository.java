package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    @Query(value = "select * from lot where archive = false", nativeQuery = true)
    List<Lot> findAllNonArchive();
}
