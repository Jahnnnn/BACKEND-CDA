package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rol")
public class Rol {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol", unique = true)
    private Integer idRol;

    /**
     * Nombre del rol
     */
    @JsonProperty("name")
    @Column(name = "NAME", length = 100, precision = 100, nullable = false)
    private String name;

    /**
     * Descripción del rol
     */
    @JsonProperty("description")
    @Column(name = "DESCRIPTION", length = 500, precision = 100, nullable = false)
    private String description;

    /**
     * Llave foranea de la entidad permisos
     */
    @JsonProperty("idPermission")
    @JoinColumn(name = "idPermission")
    @ManyToOne(fetch = FetchType.LAZY)
    private Permission permission;
}
