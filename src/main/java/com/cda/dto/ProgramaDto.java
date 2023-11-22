package com.cda.dto;

import com.cda.models.Campus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgramaDto {

    private String id;
    private String nombrePrograma;
    //Llaves foraneas
    private CampusDto campus;
    //


    public ProgramaDto(String id, String nombrePrograma, CampusDto campus) {
        this.id = id;
        this.nombrePrograma = nombrePrograma;
        this.campus = campus;
    }

    public ProgramaDto() {
    }
}
