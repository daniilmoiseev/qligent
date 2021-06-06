package com.lottrading.ltt.service;

import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BidService {
    @Autowired
    private final BidRepository bidRepository;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public Bid saveBid(Lot lot, User user, int yBid) {
        Bid bid = new Bid(yBid);
        bid.setLotId(lot.getId());
        bid.setUserId(user.getId());
        bid.setTimestamp(LocalDateTime.now());
        return bidRepository.saveAndFlush(bid);
    }

    public List<Bid> findByLotId(long lotId) {
        return bidRepository.findByLotId(lotId);
    }

    public List<Bid> findByUserId(long userId) {
        return bidRepository.findByUserId(userId);
    }
}
