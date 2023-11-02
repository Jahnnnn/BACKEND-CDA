package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "permiso_modulo")
@NoArgsConstructor
@AllArgsConstructor
public class PermisoModulo {

    @Id
    @JsonProperty("idPermiso")
    @Column(name = "ID_PERMISO", nullable = false)
    private Long idPermiso;

    @JsonProperty("nombrePermiso")
    @Column(name = "NOMBRE_PERMISO", nullable = false)
    private String nombrePermiso;

    //Llaves foraneas
    @JsonProperty("modulo")
    @JoinColumn(name = "modulo", referencedColumnName = "ID_MODULO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Modulo modulo;
    //

}
