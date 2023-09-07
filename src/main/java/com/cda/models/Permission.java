package com.cda.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "permission")
public class Permission {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERMISSION", nullable = false)
    private Integer idPermission;

    /**
     * Fecha de creación
     */
    @JsonProperty("dateCreation")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "DATE_CREATION", nullable = false, updatable = false)
    private Date dateCreation;

    /**
     * Llave foranea de la entidad funciones
     */
    @JsonProperty("idFunction")
    @Column(name = "ID_FUNCTION", nullable = false)
    @JoinColumn(name = "idFunction")
    @OneToOne(fetch = FetchType.LAZY)
    private Function function;
}
