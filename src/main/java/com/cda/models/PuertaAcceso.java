package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "puerta_acceso")
@NoArgsConstructor
@AllArgsConstructor
public class PuertaAcceso {

    @Id
    @JsonProperty("idPuertaAcceso")
    @Column(name = "ID_PUERTA_ACCESO", nullable = false)
    private Long idPuertaAcceso;

    @JsonProperty("numeroDePuerta")
    @Column(name = "NUMERO_DE_PUERTA", nullable = false)
    private Long numeroDePuerta;

    //Llaves foraneas
    @JsonProperty("bloque")
    @JoinColumn(name = "bloque", referencedColumnName = "ID_BLOQUE")
    @ManyToOne(fetch = FetchType.LAZY)
    private Bloque bloque;
    //

}
