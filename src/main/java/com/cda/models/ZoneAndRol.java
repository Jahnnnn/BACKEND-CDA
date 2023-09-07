package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "zone_and_rol")
public class ZoneAndRol {

    /**
     * Entidad que gestiona las zonas que están habilitadas para un rol
     */

    @JsonProperty("idRol")
    @JoinColumn(name = "idRol")
    @ManyToMany(fetch = FetchType.LAZY)
    private Rol rol;

    @JsonProperty("idZone")
    @JoinColumn(name = "idZone")
    @ManyToMany(fetch = FetchType.LAZY)
    private Zone zone;
}
