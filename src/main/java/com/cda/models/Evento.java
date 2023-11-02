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
@Table(name = "evento")
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @JsonProperty("idEvento")
    @Column(name = "ID_EVENTO", nullable = false)
    private Long idEvento;

    @JsonProperty("nombreEvento")
    @Column(name = "NOMBRE_EVENTO", nullable = false)
    private String nombreEvento;

    @JsonProperty("descripcionEvento")
    @Column(name = "DESCRIPCION_EVENTO", nullable = false)
    private String descripcionEvento;

}
