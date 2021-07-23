package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.BuyoutDto;
import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.Buyout;
import com.lottrading.ltt.repo.BuyoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BuyoutToEntityConverter implements Converter<BuyoutDto, Buyout> {
    @Autowired
    private BuyoutRepository buyoutRepository;

    @Override
    public Buyout convert(BuyoutDto source) {
        return buyoutRepository.findById(source.getId()).orElseThrow(NotFoundException::new);
    }
}
