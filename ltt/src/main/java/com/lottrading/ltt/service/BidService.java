package com.lottrading.ltt.service;

import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return bidRepository.saveAndFlush(bid);
    }

    public List<Bid> findByLot(Lot lot) {
        return bidRepository.findByLot(lot);
    }

    public List<Bid> findByUser(User user) {
        return bidRepository.findByUser(user);
    }
}
