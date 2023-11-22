package com.cda.dto;

import com.cda.models.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonaDto {

    private String id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private Long numeroDocumento;
    private String contrasena;
    //Llaves foraneas
    private CarnetDto carnet;
    private TipoDocumentoDto tipoDocumento;
    private ProgramaDto programa;
    private CampusDto campus;
    private ContactoDto contacto;
    private CargoDto cargo;
    private FotoDto foto;
    private RolDto rol;
    //


    public PersonaDto(String id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Long numeroDocumento, String contrasena, CarnetDto carnet, TipoDocumentoDto tipoDocumento, ProgramaDto programa, CampusDto campus, ContactoDto contacto, CargoDto cargo, FotoDto foto, RolDto rol) {
        this.id = id;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.numeroDocumento = numeroDocumento;
        this.contrasena = contrasena;
        this.carnet = carnet;
        this.tipoDocumento = tipoDocumento;
        this.programa = programa;
        this.campus = campus;
        this.contacto = contacto;
        this.cargo = cargo;
        this.foto = foto;
        this.rol = rol;
    }

    public PersonaDto() {
    }
}
