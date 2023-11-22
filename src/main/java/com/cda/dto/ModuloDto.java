package com.cda.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModuloDto {

    private String id;
    private String nombreModulo;
    private String descripcionModulo;

    public ModuloDto(String id, String nombreModulo, String descripcionModulo) {
        this.id = id;
        this.nombreModulo = nombreModulo;
        this.descripcionModulo = descripcionModulo;
    }

    public ModuloDto() {
    }
}
