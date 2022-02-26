package com.complexas;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteDto {
    private int id;
    private int weight;
    private String name;
    private String type;
    private String fromPlace;
    private String toPlace;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private ZonedDateTime dateOfSending;

    private int maxWeight;
    private String typeOfDelivery;
    private int volume;
}
