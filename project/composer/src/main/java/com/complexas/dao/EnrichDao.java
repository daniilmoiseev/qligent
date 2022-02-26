package com.complexas.dao;

import com.complexas.EnrichDto;
import com.complexas.converter.EnrichToDtoConverter;
import com.complexas.model.Enrich;
import com.complexas.repo.EnrichRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrichDao {

    private final EnrichRepository repository;
    private final EnrichToDtoConverter converter;

    public EnrichDao(EnrichRepository repository, EnrichToDtoConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Transactional(readOnly = true)
    public List<EnrichDto> read() {
        List<Enrich> enrichList = repository.findAll();
        return enrichList.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
