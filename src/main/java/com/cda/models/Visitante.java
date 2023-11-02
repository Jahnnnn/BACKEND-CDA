package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "visitante")
@NoArgsConstructor
@AllArgsConstructor
public class Visitante {

    @Id
    @JsonProperty("idVisitante")
    @Column(name = "ID_VISITANTE", nullable = false)
    private Long idVisitante;

    @JsonProperty("numeroDocumento")
    @Column(name = "NUMERO_DOCUMENTO", nullable = false)
    private String numeroDocumento;

    @JsonProperty("descripcionVisita")
    @Column(name = "DESCRIPCION_VISITA", nullable = false)
    private String descripcionVisita;

    //Llaves foraneas

    @JsonProperty("tipoDocumento")
    @JoinColumn(name = "tipoDocumento", referencedColumnName = "ID_TIPO_DOCUMENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoDocumento tipoDocumento;

    @JsonProperty("responsable")
    @JoinColumn(name = "persona", referencedColumnName = "ID_PERSONA")
    @ManyToOne(fetch = FetchType.LAZY)
    private Persona persona;

    @JsonProperty("evento")
    @JoinColumn(name = "evento", referencedColumnName = "ID_EVENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Evento evento;
    //

}
