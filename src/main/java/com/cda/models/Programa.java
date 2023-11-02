package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "programa")
@NoArgsConstructor
@AllArgsConstructor
public class Programa {

    @Id
    @JsonProperty("idPrograma")
    @Column(name = "ID_PROGRAMA", nullable = false)
    private Long idPrograma;

    @JsonProperty("nombrePrograma")
    @Column(name = "NOMBRE_PROGRAMA", nullable = false)
    private String nombrePrograma;

    //Llaves foraneas
    @JsonProperty("campus")
    @JoinColumn(name = "campus", referencedColumnName = "ID_CAMPUS")
    @ManyToOne(fetch = FetchType.LAZY)
    private Campus campus;
    //
}
