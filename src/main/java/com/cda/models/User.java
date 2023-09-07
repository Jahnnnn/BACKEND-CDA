package com.cda.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER", nullable = false)
    private Integer idUser;

    /**
     * Id de usuario que proporciona la universidad
     */
    @JsonProperty("IdUniversity")
    @Column(name = "ID_UNIVERSITY", unique = true, nullable = false)
    private Integer IdUniversity;

    /**
     * Primer nombre
     */
    @JsonProperty("firstName")
    @Column(name = "FIRST_NAME", length = 25, precision = 100, nullable = false)
    private String firstName;

    /**
     * Segundo nombre
     */
    @JsonProperty("secondName")
    @Column(name = "SECOND_NAME", length = 25, precision = 100, nullable = true)
    private String secondName;

    /**
     * Primer apellido
     */
    @JsonProperty("surname")
    @Column(name = "SURNAME", length = 25, precision = 100, nullable = false)
    private String surname;

    /**
     * Segundo apellido
     */
    @JsonProperty("secondSurname")
    @Column(name = "SECOND_SURNAME", length = 25, precision = 100, nullable = true)
    private String secondSurname;

    /**
     * Fecha de creación
     */
    @JsonProperty("dateCreation")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "DATE_CREATION", nullable = false, updatable = false)
    private Date dateCreation;

    /**
     * LLave foranea de la entidad contacto
     */
    @JsonProperty("idContact")
    @Column(name = "ID_CONTACT", nullable = false)
    @JoinColumn(name = "idContact")
    @OneToOne(fetch = FetchType.LAZY)
    private Contact contact;

}
