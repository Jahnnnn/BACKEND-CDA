package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "sede")
@NoArgsConstructor
@AllArgsConstructor
public class Sede {

    @Id
    @JsonProperty("idSede")
    @Column(name = "ID_SEDE", nullable = false)
    private Long idSede;

    @JsonProperty("nombreSede")
    @Column(name = "NOMBRE_SEDE", nullable = false)
    private String nombreSede;

    @JsonProperty("direccionSede")
    @Column(name = "DIRECCION_SEDE", nullable = false)
    private String direccionSede;

    //Llaves foraneas
    @JsonProperty("bloque")
    @JoinColumn(name = "bloque", referencedColumnName = "ID_BLOQUE")
    @ManyToOne(fetch = FetchType.LAZY)
    private Bloque bloque;
    //

}
