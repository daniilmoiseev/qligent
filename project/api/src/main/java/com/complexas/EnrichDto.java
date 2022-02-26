package com.complexas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrichDto {
    private long id;
    private String type;
    private int maxWeight;
    private String typeOfDelivery;
    private int volume;
}
