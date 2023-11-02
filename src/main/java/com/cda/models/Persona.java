package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "persona")
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @JsonProperty("idPersona")
    @Column(name = "ID_PERSONA", nullable = false)
    private Long idPersona;

    @JsonProperty("primerNombre")
    @Column(name = "PRIMER_NOMBRE", nullable = false)
    private String primerNombre;

    @JsonProperty("segundoNombre")
    @Column(name = "SEGUNDO_NOMBRE", nullable = true)
    private String segundoNombre;

    @JsonProperty("primerApellido")
    @Column(name = "PRIMER_APELLIDO", nullable = false)
    private String primerApellido;

    @JsonProperty("segundoApellido")
    @Column(name = "SEGUNDO_APELLIDO", nullable = true)
    private String segundoApellido;

    @JsonProperty("numeroDocumento")
    @Column(name = "NUMERO_DOCUMENTO", nullable = true)
    private Long numeroDocumento;

    //Llaves foraneas
    @JsonProperty("carnet")
    @JoinColumn(name = "carnet", referencedColumnName = "ID_CARNET")
    @ManyToOne(fetch = FetchType.LAZY)
    private Carnet carnet;

    @JsonProperty("tipoDocumento")
    @JoinColumn(name = "tipoDocumento", referencedColumnName = "ID_TIPO_DOCUMENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoDocumento tipoDocumento;

    @JsonProperty("programa")
    @JoinColumn(name = "programa", referencedColumnName = "ID_PROGRAMA")
    @ManyToOne(fetch = FetchType.LAZY)
    private Programa programa;

    @JsonProperty("campus")
    @JoinColumn(name = "campus", referencedColumnName = "ID_CAMPUS")
    @ManyToOne(fetch = FetchType.LAZY)
    private Campus campus;

    @JsonProperty("contacto")
    @JoinColumn(name = "contacto", referencedColumnName = "ID_CONTACTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Contacto contacto;

    @JsonProperty("cargo")
    @JoinColumn(name = "cargo", referencedColumnName = "ID_CARGO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cargo cargo;

    @JsonProperty("foto")
    @JoinColumn(name = "foto", referencedColumnName = "ID_FOTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Foto foto;

    //

}
