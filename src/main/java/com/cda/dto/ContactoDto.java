package com.cda.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactoDto {

    private String id;
    private String direccionResidencia;
    private Long telefonoCelular;
    private String correoElectronico;

    public ContactoDto(String id, String direccionResidencia, Long telefonoCelular, String correoElectronico) {
        this.id = id;
        this.direccionResidencia = direccionResidencia;
        this.telefonoCelular = telefonoCelular;
        this.correoElectronico = correoElectronico;
    }

    public ContactoDto() {
    }
}
