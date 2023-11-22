package com.cda.models;

import com.cda.dto.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @JsonProperty("primerNombre")
    private String primerNombre;

    @JsonProperty("segundoNombre")
    private String segundoNombre;

    @JsonProperty("primerApellido")
    private String primerApellido;

    @JsonProperty("segundoApellido")
    private String segundoApellido;

    @JsonProperty("numeroDocumento")
    private Long numeroDocumento;

    @JsonProperty("contrasena")
    private String contrasena;

    //Llaves foraneas
    @JsonProperty("carnet")
    private CarnetDto carnet;

    @JsonProperty("tipoDocumento")
    private TipoDocumentoDto tipoDocumento;

    @JsonProperty("programa")
    private ProgramaDto programa;

    @JsonProperty("campus")
    private CampusDto campus;

    @JsonProperty("contacto")
    private ContactoDto contacto;

    @JsonProperty("cargo")
    private CargoDto cargo;

    @JsonProperty("foto")
    private FotoDto foto;

    @JsonProperty("rol")
    private RolDto rol;

    //

}
