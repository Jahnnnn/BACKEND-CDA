package com.cda.models;

import com.cda.dto.ModuloDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermisoModulo {

    @JsonProperty("nombrePermiso")
    private String nombrePermiso;

    //Llaves foraneas
    @JsonProperty("modulo")
    private ModuloDto modulo;
    //

}
