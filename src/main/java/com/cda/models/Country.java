package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "country")
public class Country {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COUNTRY", unique = true)
    private Integer idCountry;

    /**
     * Nombre del pais
     */
    @JsonProperty("name")
    @Column(name = "NAME", length = 100, precision = 100, nullable = false)
    private String name;
}
