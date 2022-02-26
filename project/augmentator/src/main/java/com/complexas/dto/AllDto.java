package com.complexas.dto;

import com.complexas.EnrichDto;
import com.complexas.ParcelDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Deprecated(since = "Augmentation process reworked")
public class AllDto {

    public static List<ParcelDto> parcelFromKafka = new ArrayList<>();
    public static List<EnrichDto> enrichFromKafka = new ArrayList<>();
}
