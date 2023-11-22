package com.cda.dto;

import com.cda.models.Modulo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermisoModuloDto {

    private String id;
    private String nombrePermiso;
    //Llaves foraneas
    private ModuloDto modulo;
    //


    public PermisoModuloDto(String id, String nombrePermiso, ModuloDto modulo) {
        this.id = id;
        this.nombrePermiso = nombrePermiso;
        this.modulo = modulo;
    }

    public PermisoModuloDto() {
    }
}
