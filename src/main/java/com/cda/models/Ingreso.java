package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "ingreso")
@NoArgsConstructor
@AllArgsConstructor
public class Ingreso {

    @Id
    @JsonProperty("idIngreso")
    @Column(name = "ID_INGRESO", nullable = false)
    private Long idIngreso;

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
