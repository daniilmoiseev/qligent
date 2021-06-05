package com.lottrading.ltt.service;

import com.lottrading.ltt.models.Buyout;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.repo.BuyoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyoutService {
    @Autowired
    private final BuyoutRepository buyoutRepository;

    public BuyoutService(BuyoutRepository buyoutRepository) {
        this.buyoutRepository = buyoutRepository;
    }

    public Buyout saveBuyout(Lot lot, User user) {
        Buyout buyout = new Buyout(lot.getBuyout());
        buyout.setLotId(lot.getId());
        buyout.setUserId(user.getId());
        return buyoutRepository.saveAndFlush(buyout);
    }

    public List<Buyout> findByUser(User user) {
        return buyoutRepository.findByUser(user);
    }
}
