package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "zone")
public class Zone {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ZONE", unique = true)
    private Integer idZone;

    /**
     * Nombre de la zona
     */
    @JsonProperty("name")
    @Column(name = "NAME", length = 100, precision = 100, nullable = false)
    private String name;

    /**
     * Descripción de la zona
     */
    @JsonProperty("description")
    @Column(name = "DESCRIPTION", length = 500, precision = 100, nullable = false)
    private String description;

    /**
     * Estado de la zona
     */
    @JsonProperty("status")
    @Column(name = "STATUS", nullable = false)
    private byte status;
}
