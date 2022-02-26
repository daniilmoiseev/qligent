package com.complexas.controller;

import com.complexas.CompleteDto;
import com.complexas.ParcelDto;
import com.complexas.dto.AllDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BackController {

    @GetMapping("/parcel-drop")
    public List<ParcelDto> getParcel() {
        return AllDto.parcelFromKafka;
    }

    @GetMapping("/complete-out")
    public List<CompleteDto> getComplete() {
        return AllDto.completeFromKafka;
    }
}
