package com.cda.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventoDto {

    private String id;
    private String nombreEvento;
    private String descripcionEvento;

    public EventoDto(String id, String nombreEvento, String descripcionEvento) {
        this.id = id;
        this.nombreEvento = nombreEvento;
        this.descripcionEvento = descripcionEvento;
    }

    public EventoDto() {
    }
}
