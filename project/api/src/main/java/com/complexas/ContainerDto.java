package com.complexas;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContainerDto {

    private int id;
    private int countContainers;
    private int period;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:MM:ss", timezone = "UTC")
    private ZonedDateTime dateOfEmulation;
    private List<ParcelDto> parcels;
}
