package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "department")
public class Department {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEPARTMENT", unique = true)
    private Integer idDepartment;

    /**
     * Nombre del departamento
     */
    @JsonProperty("name")
    @Column(name = "NAME", length = 100, precision = 100, nullable = false)
    private String name;

    /**
     * Llave foranea de la entidad pais
     */
    @JsonProperty("idCountry")
    @Column(name = "ID_COUNTRY", nullable = false)
    @JoinColumn(name = "idCountry")
    @OneToMany(fetch = FetchType.LAZY)
    private Country country;

}
