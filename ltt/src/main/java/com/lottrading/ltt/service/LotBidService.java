package com.lottrading.ltt.service;

import com.lottrading.ltt.repo.LotBidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotBidService {
    @Autowired
    private final LotBidRepository lotBidRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LotService lotService;

    public LotBidService(LotBidRepository lotBidRepository) {
        this.lotBidRepository = lotBidRepository;
    }


}
