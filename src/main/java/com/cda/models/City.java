package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "city")
public class City {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CITY", unique = true)
    private Integer idCity;

    /**
     * Nombre de la ciudad
     */
    @JsonProperty("name")
    @Column(name = "NAME", length = 100, precision = 100, nullable = false)
    private String name;

    /**
     * Llave foranea de la entidad departamento
     */
    @JsonProperty("idDepartment")
    @JoinColumn(name = "idDepartment")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

}
