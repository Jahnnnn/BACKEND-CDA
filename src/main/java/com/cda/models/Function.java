package com.cda.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "function")
public class Function {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FUNCTION", unique = true)
    private Integer idFunction;

    /**
     * Nombre de la función o servicio
     */
    @JsonProperty("name")
    @Column(name = "NAME", length = 500, precision = 100, nullable = false)
    private String name;

    /**
     * Descripción de la función o servicio
     */
    @JsonProperty("description")
    @Column(name = "DESCRIPTION", length = 1000, precision = 100, nullable = false)
    private String description;

    /**
     * Fecha de creación
     */
    @JsonProperty("dateCreation")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "DATE_CREATION", nullable = false, updatable = false)
    private Date dateCreation;

    /**
     * Llave foranea de la entidad módulo
     */
    @JsonProperty("idModule")
    @JoinColumn(name = "idModule")
    @OneToOne(fetch = FetchType.LAZY)
    private Module module;

}
