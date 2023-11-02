package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "modulo")
@NoArgsConstructor
@AllArgsConstructor
public class Modulo {

    @Id
    @JsonProperty("idModulo")
    @Column(name = "ID_MODULO", nullable = false)
    private Long idModulo;

    @JsonProperty("nombreModulo")
    @Column(name = "NOMBRE_MODULO", nullable = false)
    private String nombreModulo;

    @JsonProperty("descripcionModulo")
    @Column(name = "DESCRIPCION_MODULO", nullable = false)
    private String descripcionModulo;

}
