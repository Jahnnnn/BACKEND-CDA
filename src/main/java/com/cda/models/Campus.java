package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "campus")
@NoArgsConstructor
@AllArgsConstructor
public class Campus {

    @Id
    @JsonProperty("idCampus")
    @Column(name = "ID_CAMPUS", nullable = false)
    private Long idCampus;

    @JsonProperty("nombreDelCampus")
    @Column(name = "NOMBRE_DEL_CAMPUS", nullable = false)
    private String nombreDelCampus;

    //Llaves foraneas
    @JsonProperty("sede")
    @JoinColumn(name = "sede", referencedColumnName = "ID_SEDE")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sede sede;
    //
}
