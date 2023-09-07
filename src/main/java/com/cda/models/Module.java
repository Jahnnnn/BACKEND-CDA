package com.cda.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "module")
public class Module {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODULE", unique = true)
    private Integer idModule;

    /**
     * Nombre del módulo
     */
    @JsonProperty("name")
    @Column(name = "NAME", length = 100, precision = 100, nullable = false, updatable = false)
    private String name;

    /**
     * Fecha de creación
     */
    @JsonProperty("dateCreation")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "DATE_CREATION", nullable = false, updatable = false)
    private Date dateCreation;
}
