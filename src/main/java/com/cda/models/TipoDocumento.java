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
@Table(name = "tipo_documento")
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumento {

    @Id
    @JsonProperty("idTipoDocumento")
    @Column(name = "ID_TIPO_DOCUMENTO", nullable = false)
    private Long idTipoDocumento;

    @JsonProperty("sigla")
    @Column(name = "SIGLA", nullable = false)
    private String sigla;

    @JsonProperty("descripcion")
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

}
