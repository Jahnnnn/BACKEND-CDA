package com.cda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CargoDto {
    private String id;
    private String nombreCargo;

    public CargoDto(String id, String nombreCargo) {
        this.id = id;
        this.nombreCargo = nombreCargo;
    }

    public CargoDto() {
    }
}
