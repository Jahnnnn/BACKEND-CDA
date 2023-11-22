package com.cda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class CarnetDto {

    private String id;
    private Long codigoUniversidad;
    private Long serialCarnet;

    public CarnetDto(String id, Long codigoUniversidad, Long serialCarnet) {
        this.id = id;
        this.codigoUniversidad = codigoUniversidad;
        this.serialCarnet = serialCarnet;
    }

    public CarnetDto() {
    }
}
