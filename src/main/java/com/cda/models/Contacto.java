package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {
    
    @JsonProperty("direccionResidencia")
    private String direccionResidencia;

    @JsonProperty("telefonoCelular")
    private Long telefonoCelular;

    @JsonProperty("correoElectronico")
    private String correoElectronico;

}
