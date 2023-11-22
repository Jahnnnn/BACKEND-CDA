package com.cda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoDocumentoDto {

    private String id;
    private String sigla;
    private String descripcion;

    public TipoDocumentoDto(String id, String sigla, String descripcion) {
        this.id = id;
        this.sigla = sigla;
        this.descripcion = descripcion;
    }

    public TipoDocumentoDto() {
    }
}
