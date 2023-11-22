package com.cda.models;

import com.cda.dto.EventoDto;
import com.cda.dto.PersonaDto;
import com.cda.dto.TipoDocumentoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitante {

    @JsonProperty("numeroDocumento")
    private String numeroDocumento;

    @JsonProperty("descripcionVisita")
    private String descripcionVisita;

    //Llaves foraneas
    @JsonProperty("tipoDocumento")
    private TipoDocumentoDto tipoDocumento;

    @JsonProperty("responsable")
    private PersonaDto persona;

    @JsonProperty("evento")
    private EventoDto evento;
    //

}
