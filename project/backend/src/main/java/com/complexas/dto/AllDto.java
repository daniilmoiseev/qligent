package com.complexas.dto;

import com.complexas.CompleteDto;
import com.complexas.ParcelDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AllDto {

    public static List<ParcelDto> parcelFromKafka = new ArrayList<>();
    public static List<CompleteDto> completeFromKafka = new ArrayList<>();
}
