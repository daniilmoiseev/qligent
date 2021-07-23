package com.lottrading.ltt.service;

import com.lottrading.ltt.dao.LotDao;
import com.lottrading.ltt.dto.LotDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotService {
    @Autowired
    private final LotDao lotDao;

    public LotService(LotDao lotDao) {
        this.lotDao = lotDao;
    }

    public LotDto createLot(String title, int buyout, int minBid) {
        return lotDao.createLot(title, buyout, minBid);
    }

    public LotDto deleteLot(Long lotId, Long userId, int buyout) {
        return lotDao.deleteLot(lotId, userId, buyout);
    }

    public LotDto offerBid(Long lotId, Long userId, int yBid) {
        return lotDao.offerBid(lotId, userId, yBid);
    }

    public List<LotDto> findAll() {
        return lotDao.findAll();
    }

    public LotDto getOneLot(long id) {
        return lotDao.getOneLot(id);
    }
}