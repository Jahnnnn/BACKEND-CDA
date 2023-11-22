package com.cda.dto;

import com.cda.models.Bloque;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SedeDto {

    private String id;
    private String nombreSede;
    private String direccionSede;
    //Llaves foraneas
    private BloqueDto bloque;
    //


    public SedeDto(String id, String nombreSede, String direccionSede, BloqueDto bloque) {
        this.id = id;
        this.nombreSede = nombreSede;
        this.direccionSede = direccionSede;
        this.bloque = bloque;
    }

    public SedeDto() {
    }
}
