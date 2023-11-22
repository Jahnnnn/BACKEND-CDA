package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumento {
    
    @JsonProperty("sigla")
    private String sigla;

    @JsonProperty("descripcion")
    private String descripcion;

}
