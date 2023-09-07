package com.cda.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "contact")
public class Contact {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTACT", unique = true)
    private Integer idContact;

    /**
     * Correo electrónico
     */
    @JsonProperty("email")
    @Column(name = "EMAIL", length = 200, precision = 100, nullable = false)
    private String email;

    /**
     * Telefono
     */
    @JsonProperty("telephone")
    @Column(name = "TELEPHONE", nullable = false)
    private Integer telephone;

    /**
     * Numero de identificación
     */
    @JsonProperty("numberIdentification")
    @Column(name = "NUMBER_IDENTIFICATION", nullable = false)
    private Integer numberIdentification;

    /**
     * Dirección de residencia
     */
    @JsonProperty("address")
    @Column(name = "ADDRESS", length = 100, precision = 100, nullable = false)
    private String address;

    /**
     * Fecha de creación
     */
    @JsonProperty("dateCreation")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "DATE_CREATION", nullable = false, updatable = false)
    private Date dateCreation;

    /**
     * Llave foranea de la entidad ciudad
     */
    @JsonProperty("idCity")
    @JoinColumn(name = "idCity")
    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    /**
     * LLave foranea de la entidad tipos de idenficación
     */
    @JsonProperty("idTypeIdentification")
    @JoinColumn(name = "idTypeIdentification")
    @ManyToOne(fetch = FetchType.LAZY)
    private TypeIdentifications typeIdentifications;
}
