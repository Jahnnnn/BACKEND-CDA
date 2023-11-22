package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foto {
    
    @JsonProperty("foto")
    private String foto;

    @JsonProperty("fechaDeCreacion")
    private Date fechaDeCreacion;

}
