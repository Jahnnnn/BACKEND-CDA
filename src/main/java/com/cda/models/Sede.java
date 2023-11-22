package com.cda.models;

import com.cda.dto.BloqueDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sede {

    @JsonProperty("nombreSede")
    private String nombreSede;

    @JsonProperty("direccionSede")
    private String direccionSede;

    //Llaves foraneas
    @JsonProperty("bloque")
    private BloqueDto bloque;
    //

}
