package com.cda.models;

import com.cda.dto.PersonaDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salida {

    @JsonProperty("fecha")
    private Date fecha;

    //Llaves foraneas
    @JsonProperty("persona")
    private PersonaDto persona;
    //
}
