package com.lottrading.ltt.service;

import com.lottrading.ltt.exception.MyIOException;
import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.*;
import com.lottrading.ltt.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LotService {
    @Autowired
    private final LotRepository lotRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final BidService bidService;

    @Autowired
    private final BuyoutService buyoutService;

    public LotService(LotRepository lotRepository, UserService userService, BidService bidService, BuyoutService buyoutService) {
        this.lotRepository = lotRepository;
        this.userService = userService;
        this.bidService = bidService;
        this.buyoutService = buyoutService;
    }

    public Lot createLot(String title, int buyout, int minBid) {
        Lot lot = new Lot(title, buyout, minBid, false, new ArrayList<>());
        return lotRepository.saveAndFlush(lot);
    }

    public Lot deleteLot(long lotId, long userId) {
        Lot lot = getOneLot(lotId);
        if(!lot.isArchive()) {
            User user = userService.getOneUser(userId);
            List<Buyout> buyouts = user.getBuyouts();
            Buyout buyout = buyoutService.saveBuyout(lot, user);
            buyouts.add(buyout);
            user.setBuyouts(buyouts);
            lot.setArchive(true);
            userService.updateUser(user);
        }
        return lotRepository.saveAndFlush(lot);
    }

    public Lot updateLot(long lotId, long userId, int yBid) {
        Lot lot = getOneLot(lotId);
        List<Bid> bids = lot.getBids();

        User user = userService.getOneUser(userId);

        if(bids.isEmpty()){
            Bid bid = bidService.saveBid(lot, user, yBid);
            bids.add(bid);
            lot.setBids(bids);
            user.setBids(bids);
        } else {
            Bid lastBid = bids.get(bids.size()-1);
            if(lastBid.getBid() < yBid && lot.getMinBid() <= yBid && lastBid.getUserId() != userId) {
                Bid bid = bidService.saveBid(lot, user, yBid);
                bids.add(bid);
                lot.setBids(bids);
                user.setBids(bids);
            }
            else throw new MyIOException();
        }
        userService.updateUser(user);
        return lotRepository.saveAndFlush(lot);
    }

    public List<Lot> findAll() {
        return lotRepository.findAllNonArchive();
    }

    public Lot getOneLot(long id) {
        return lotRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}