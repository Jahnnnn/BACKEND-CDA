package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "rol")
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @JsonProperty("idRol")
    @Column(name = "ID_ROL", nullable = false)
    private Long idRol;

    @JsonProperty("nombreRol")
    @Column(name = "NOMBRE_ROL", nullable = false)
    private String nombreRol;

    //Llaves foraneas
    @JsonProperty("permiso")
    @JoinColumn(name = "permiso", referencedColumnName = "ID_PERMISO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PermisoModulo permiso;
    //

}
