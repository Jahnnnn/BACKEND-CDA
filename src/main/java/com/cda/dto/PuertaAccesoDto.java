package com.cda.dto;

import com.cda.models.Bloque;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PuertaAccesoDto {

    private String id;
    private Long numeroDePuerta;
    //Llaves foraneas
    private BloqueDto bloque;
    //


    public PuertaAccesoDto(String id, Long numeroDePuerta, BloqueDto bloque) {
        this.id = id;
        this.numeroDePuerta = numeroDePuerta;
        this.bloque = bloque;
    }

    public PuertaAccesoDto() {
    }
}
