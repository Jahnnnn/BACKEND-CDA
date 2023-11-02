package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "foto")
@NoArgsConstructor
@AllArgsConstructor
public class Foto {

    @Id
    @JsonProperty("idFoto")
    @Column(name = "ID_FOTO", nullable = false)
    private Long idFoto;

    @JsonProperty("foto")
    @Column(name = "FOTO", nullable = false)
    private String foto;

    @JsonProperty("fechaDeCreacion")
    @Column(name = "FECHA_DE_CREACION", nullable = false)
    private Date fechaDeCreacion;

}
