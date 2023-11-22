package com.cda.dto;

import com.cda.models.Persona;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class SalidaDto {
    private String id;
    private Date fecha;
    //Llaves foraneas
    private PersonaDto persona;
    //


    public SalidaDto(String id, Date fecha, PersonaDto persona) {
        this.id = id;
        this.fecha = fecha;
        this.persona = persona;
    }

    public SalidaDto() {
    }
}
