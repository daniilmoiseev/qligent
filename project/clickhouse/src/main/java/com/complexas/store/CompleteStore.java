package com.complexas.store;

import com.complexas.CompleteDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class CompleteStore {

    private final CopyOnWriteArrayList<CompleteDto> completes;

    public CompleteStore() {
        this.completes = new CopyOnWriteArrayList<>();
    }

    public void add(CompleteDto completeDto) {
        completes.add(completeDto);
    }

    public CopyOnWriteArrayList<CompleteDto> getAll() {
        return completes;
    }
}
