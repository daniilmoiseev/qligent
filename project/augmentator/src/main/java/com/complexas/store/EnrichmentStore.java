package com.complexas.store;

import com.complexas.EnrichDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EnrichmentStore {
    private Map<String, EnrichDto> enrichDtoMap;

    public EnrichmentStore() {
        this.enrichDtoMap = new ConcurrentHashMap<>();
    }

    public void put(EnrichDto enrichDto) {
        enrichDtoMap.put(enrichDto.getType(), enrichDto);
    }

    public EnrichDto get(String type) {
        return enrichDtoMap.get(type);
    }
}
