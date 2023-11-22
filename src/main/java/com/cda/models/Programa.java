package com.cda.models;

import com.cda.dto.CampusDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Programa {

    @JsonProperty("nombrePrograma")
    private String nombrePrograma;

    //Llaves foraneas
    @JsonProperty("campus")
    private CampusDto campus;
    //
}
