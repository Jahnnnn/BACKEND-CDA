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
@Table(name = "cargo")
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {

    @Id
    @JsonProperty("idCargo")
    @Column(name = "ID_CARGO", nullable = false)
    private Long idCargo;

    @JsonProperty("nombreCargo")
    @Column(name = "NOMBRE_CARGO", nullable = false)
    private String nombreCargo;

}
