package com.cda.models;

import com.cda.dto.SedeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campus {

    @JsonProperty("nombreDelCampus")
    private String nombreDelCampus;

    //Llaves foraneas
    @JsonProperty("sede")
    private SedeDto sede;
    //
}
