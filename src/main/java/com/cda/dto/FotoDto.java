package com.cda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class FotoDto {

    private String id;
    private String foto;
    private Date fechaDeCreacion;

    public FotoDto(String id, String foto, Date fechaDeCreacion) {
        this.id = id;
        this.foto = foto;
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public FotoDto() {
    }
}
