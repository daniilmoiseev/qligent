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
        Lot lot = new Lot(title, buyout, minBid, 60, false, new ArrayList<>());
        return lotRepository.saveAndFlush(lot);
    }

    public Lot deleteLot(long lotId, long userId) {
        Lot lot = getOneLot(lotId);
        User user = userService.getOneUser(userId);
        if(!lot.isArchive() && user.getCash() >= lot.getBuyout()) {
            user.setCash(user.getCash() - lot.getBuyout());
            userService.updateUser(user);
            buyoutService.saveBuyout(lot, user);
            lot.setArchive(true);
            lotRepository.saveAndFlush(lot);
        } else throw new MyIOException();
        return getOneLot(lotId);
    }

    public Lot updateLot(long lotId, long userId, int yBid) {
        Lot lot = getOneLot(lotId);
        List<Bid> bids = lot.getBids();

        User user = userService.getOneUser(userId);

        if(bids.isEmpty()){
            if(user.getCash() >= yBid && lot.getMinBid() <= yBid) bidService.saveBid(lot, user, yBid);
            else throw new MyIOException();
        } else {
            Bid lastBid = bids.get(bids.size()-1);
            if(lastBid.getBid() < yBid && lot.getMinBid() <= yBid && lastBid.getUserId() != userId && user.getCash() >= yBid) {
                bidService.saveBid(lot, user, yBid);
            }
            else throw new MyIOException();
        }
        return getOneLot(lotId);
    }

    public List<Lot> findAll() {
        List<Lot> lots = lotRepository.findAllNonArchive();
        lots.forEach(it -> {
            List<Bid> bids = bidService.findByLotId(it.getId());
            if(!bids.isEmpty()){
                it.setBids(bids);
            } else {
                it.setBids(new ArrayList<>());
            }
//            it.setBids(bidService.findByLotId(it.getId()));
        });
        return lots;
    }

    public Lot getOneLot(long id) {
        Lot lot = lotRepository.findById(id).orElseThrow(NotFoundException::new);
        List<Bid> bids = bidService.findByLotId(lot.getId());
        if(!bids.isEmpty()){
            lot.setBids(bids);
        } else {
            lot.setBids(new ArrayList<>());
        }
//        lot.setBids(bidService.findByLotId(lot.getId()));
        return lot;
    }
}