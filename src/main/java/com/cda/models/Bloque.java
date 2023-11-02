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
@Table(name = "bloque")
@NoArgsConstructor
@AllArgsConstructor
public class Bloque {

    @Id
    @JsonProperty("idBloque")
    @Column(name = "ID_BLOQUE", nullable = false)
    private Long idBloque;

    @JsonProperty("numeroDeBloque")
    @Column(name = "NUMERO_DE_BLOQUE", nullable = false)
    private Long numeroDeBloque;

}
