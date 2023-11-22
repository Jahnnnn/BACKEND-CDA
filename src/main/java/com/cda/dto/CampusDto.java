package com.cda.dto;

import com.cda.models.Sede;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampusDto {

    private String id;
    private String nombreDelCampus;
    //Llaves foraneas
    private SedeDto sede;
    //


    public CampusDto(String id, String nombreDelCampus, SedeDto sede) {
        this.id = id;
        this.nombreDelCampus = nombreDelCampus;
        this.sede = sede;
    }

    public CampusDto() {
    }
}
