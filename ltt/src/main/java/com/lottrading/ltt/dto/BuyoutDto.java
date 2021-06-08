package com.lottrading.ltt.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class BuyoutDto {
    private long id;
    private int buyout;
    private ZonedDateTime zonedDateTime;
    private long lotId;
    private long userId;
}