package com.lottrading.ltt.config;

import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class CheckTimeBuyoutLotConfig {
    @Autowired
    private LotService lotService;

    @Scheduled(fixedDelay = 60000)
    public void checkTime(){
        List<LotDto> lots = lotService.findAll();
        if(!lots.isEmpty()) {
            lots.forEach(it -> {
                if(it.getBuyoutTime() == 0) {
                    List<BidDto> bids = it.getBids();
                    BidDto bid = bids.get(bids.size() - 1);
                    lotService.deleteLot(it.getId(), bid.getUserId(), bid.getBid());
                }
            });
        }
    }
}
