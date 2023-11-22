package com.cda.dto;

import com.cda.models.Evento;
import com.cda.models.Persona;
import com.cda.models.TipoDocumento;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitanteDto {

    private String id;
    private String numeroDocumento;
    private String descripcionVisita;
    //Llaves foraneas
    private TipoDocumentoDto tipoDocumento;
    private PersonaDto persona;
    private EventoDto evento;
    //


    public VisitanteDto(String id, String numeroDocumento, String descripcionVisita, TipoDocumentoDto tipoDocumento, PersonaDto persona, EventoDto evento) {
        this.id = id;
        this.numeroDocumento = numeroDocumento;
        this.descripcionVisita = descripcionVisita;
        this.tipoDocumento = tipoDocumento;
        this.persona = persona;
        this.evento = evento;
    }

    public VisitanteDto() {
    }
}
