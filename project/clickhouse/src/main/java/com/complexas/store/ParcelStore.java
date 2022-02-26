package com.complexas.store;

import com.complexas.ParcelDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ParcelStore {

    private final CopyOnWriteArrayList<ParcelDto> parcels;

    public ParcelStore() {
        this.parcels = new CopyOnWriteArrayList<>();
    }

    public void add(ParcelDto parcelDto) {
        parcels.add(parcelDto);
    }

    public CopyOnWriteArrayList<ParcelDto> getAll() {
        return parcels;
    }
}
