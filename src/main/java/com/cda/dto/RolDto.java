package com.cda.dto;

import com.cda.models.PermisoModulo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RolDto {

    private String id;
    private String nombreRol;
    //Llaves foraneas
    private PermisoModuloDto permiso;
    //


    public RolDto(String id, String nombreRol, PermisoModuloDto permiso) {
        this.id = id;
        this.nombreRol = nombreRol;
        this.permiso = permiso;
    }

    public RolDto() {
    }
}
