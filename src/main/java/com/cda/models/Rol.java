package com.cda.models;

import com.cda.dto.PermisoModuloDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @JsonProperty("nombreRol")
    private String nombreRol;

    //Llaves foraneas
    @JsonProperty("permiso")
    private PermisoModuloDto permiso;
    //

}
