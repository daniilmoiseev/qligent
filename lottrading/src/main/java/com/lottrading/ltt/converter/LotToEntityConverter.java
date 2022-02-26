package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LotToEntityConverter implements Converter<LotDto, Lot> {

    private LotRepository lotRepository;

    public LotToEntityConverter(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public Lot convert(LotDto source) {
        Lot lot = lotRepository.findById(source.getId()).orElseThrow(NotFoundException::new);
        lot.setBuyoutTime(source.getBuyoutTime());
        lot.setArchive(source.isArchive());
        return lot;
    }
}
