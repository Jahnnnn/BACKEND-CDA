package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "type_identifications")
public class TypeIdentifications {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TYPE_IDENTIFICATION", nullable = false)
    private Integer idTypeIdentification;

    /**
     * Abreviación o sigla del tipo de identificación
     */
    @JsonProperty("abbreviation")
    @Column(name = "ABBREVIATION", length = 25, precision = 100, nullable = false)
    private String abbreviation;

    /**
     * Nombre del tipo de identificación
     */
    @JsonProperty("name")
    @Column(name = "NAME", length = 25, precision = 100, nullable = false)
    private String name;

    /**
     * Descripción de la identificación
     */
    @JsonProperty("description")
    @Column(name = "DESCRIPTION", length = 25, precision = 100, nullable = false)
    private String description;


}
