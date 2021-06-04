package com.lottrading.ltt.service;

import com.lottrading.ltt.exception.MyIOException;
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
    private UserService userService;

    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    public Lot createLot(String title, int buyout, int minBid) {
        Lot lot = new Lot(title, buyout, minBid, false, new ArrayList<>());
        return lotRepository.saveAndFlush(lot);
    }

    public Lot deleteLot(long lotId, long userId) {
        Lot lot = getOneLot(lotId);
        if(!lot.isArchive()) {
            User user = userService.getOneUser(userId);
            List<UserBuyout> userBuyouts = user.getUserBuyouts();
            userBuyouts.add(new UserBuyout(){{
                setLotId(lotId);
                setBuyout(lot.getBuyout());
            }});
            user.setUserBuyouts(userBuyouts);
            lot.setArchive(true);
            userService.updateUser(user);
        }
        return lotRepository.saveAndFlush(lot);
    }

    public Lot updateLot(long lotId, long userId, int bid) {
        Lot lot = getOneLot(lotId);
        List<LotBid> lotBids = lot.getLotBids();

        User user = userService.getOneUser(userId);
        List<UserBid> userBids = user.getUserBids();

        if(lotBids.isEmpty()){
            lotBids.add(new LotBid(){{
                setUserId(userId);
                setBid(bid);
            }});
            lot.setLotBids(lotBids);

            userBids.add(new UserBid(){{
                setLotId(lotId);
                setBid(bid);
            }});
            user.setUserBids(userBids);
        } else {
            LotBid lastBid = lotBids.get(lotBids.size()-1);
            if(lastBid.getBid() <= bid && lot.getMinBid() <= bid && lastBid.getUserId() != userId) {
                lotBids.add(new LotBid(){{
                    setUserId(userId);
                    setBid(bid);
                }});
                lot.setLotBids(lotBids);

                userBids.add(new UserBid(){{
                    setLotId(lotId);
                    setBid(bid);
                }});
                user.setUserBids(userBids);
            }
            else throw new MyIOException();
        }
        userService.updateUser(user);

        return lotRepository.saveAndFlush(lot);
    }

    public List<Lot> findAll() {
        return lotRepository.findAll();
    }

    public Lot getOneLot(long id) {
        return lotRepository.getOne(id);
    }
}