package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carnet {

    @JsonProperty("codigoUniversidad")
    private Long codigoUniversidad;

    @JsonProperty("serialCarnet")
    private Long serialCarnet;

}
