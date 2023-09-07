package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "user_and_rol")
public class UserAndRol {

    /**
     * Entidad que gestiona los roles que pueden tener los usuarios
     */

    @JsonProperty("idUser")
    @JoinColumn(name = "idUser")
    @ManyToMany(fetch = FetchType.LAZY)
    private User user;

    @JsonProperty("idRol")
    @JoinColumn(name = "idRol")
    @ManyToMany(fetch = FetchType.LAZY)
    private Rol rol;

    /**
     * Fecha de creación
     */
    @JsonProperty("dateCreation")
    @Column(name = "DATE_CREATION", nullable = false, updatable = false)
    private Date dateCreation;


}
