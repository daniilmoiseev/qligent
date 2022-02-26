package com.complexas.service;

import com.complexas.EnrichDto;
import com.complexas.dao.EnrichDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrichService {

    private final EnrichDao enrichDao;
    private final Producer producer;

    public EnrichService(EnrichDao enrichDao, Producer producer) {
        this.enrichDao = enrichDao;
        this.producer = producer;
    }

    public List<EnrichDto> pushToKafka() {
        List<EnrichDto> enrichDtoList = enrichDao.read();
        enrichDtoList.forEach(producer::sendMessage);

        return enrichDtoList;
    }

    public List<EnrichDto> getAllDto() {
        return enrichDao.read();
    }
}
