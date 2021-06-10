package com.lottrading.ltt.config;

import com.lottrading.ltt.converter.LotToEntityConverter;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.repo.LotRepository;
import com.lottrading.ltt.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class EditTimeBuyoutLotConfig {
    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private LotService lotService;

    @Autowired
    private LotToEntityConverter lotToEntityConverter;

    @Async
    @Scheduled(fixedRate = 1000)
    public void editTime(){
        List<LotDto> lots = lotService.findAll();
        if(!lots.isEmpty()) {
            lots.forEach(it -> {
                if(!it.getBids().isEmpty()) {
                    if(it.getBuyoutTime() > 0) {
                        it.setBuyoutTime(it.getBuyoutTime() - 1);
                        lotRepository.saveAndFlush(lotToEntityConverter.convert(it));
                    }
                }
            });
        }
    }
}
