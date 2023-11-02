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
@Table(name = "carnet")
@NoArgsConstructor
@AllArgsConstructor
public class Carnet {

    @Id
    @JsonProperty("idCarnet")
    @Column(name = "ID_CARNET", nullable = false)
    private Long idCarnet;

    @JsonProperty("codigoUniversidad")
    @Column(name = "CODIGO_UNIVERSIDAD", nullable = false)
    private Long codigoUniversidad;

    @JsonProperty("serialCarnet")
    @Column(name = "SERIAL_CARNET", nullable = false)
    private Long serialCarnet;

}
