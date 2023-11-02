package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data
@Table(name = "salida")
@NoArgsConstructor
@AllArgsConstructor
public class Salida {

    @Id
    @JsonProperty("idSalida")
    @Column(name = "ID_SALIDA", nullable = false)
    private Long idSalida;

    @JsonProperty("fecha")
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    //Llaves foraneas
    @JsonProperty("persona")
    @JoinColumn(name = "persona", referencedColumnName = "ID_PERSONA")
    @ManyToOne(fetch = FetchType.LAZY)
    private Persona persona;
    //
}
