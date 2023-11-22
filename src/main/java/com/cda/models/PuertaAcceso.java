package com.cda.models;

import com.cda.dto.BloqueDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuertaAcceso {

    @JsonProperty("numeroDePuerta")
    private Long numeroDePuerta;

    //Llaves foraneas
    @JsonProperty("bloque")
    private BloqueDto bloque;
    //

}
