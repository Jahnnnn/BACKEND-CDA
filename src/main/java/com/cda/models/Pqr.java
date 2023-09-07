package com.cda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pqr")
public class Pqr {

    /**
     * Id de la clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PQR", unique = true)
    private Integer idPqr;

    /**
     * Correo electrónico
     */
    @JsonProperty("email")
    @Column(name = "EMAIL", length = 200, precision = 100, nullable = false)
    private String email;

    /**
     * Nombres
     */
    @JsonProperty("names")
    @Column(name = "NAMES", length = 150, precision = 100, nullable = false)
    private String names;

    /**
     * Asunto
     */
    @JsonProperty("affair")
    @Column(name = "AFFAIR", length = 100000, precision = 100, nullable = false)
    private String affair;

    /**
     * Mensaje
     */
    @JsonProperty("message")
    @Column(name = "MESSAGE", length = 1000000, nullable = false)
    private String message;
}
