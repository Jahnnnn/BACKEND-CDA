package com.cda.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "notification")
public class Notification {

    /**
     * Id de clase
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTIFICATION", unique = true)
    private Integer idNotification;

    /**
     * Mensaje de la notificación
     */
    @JsonProperty("message")
    @Column(name = "MESSAGE", length = 10000, nullable = false)
    private String message;

    /**
     * Fecha de inicio
     */
    @JsonProperty("dateStart")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "DATE_START", nullable = false, updatable = false)
    private Date dateStart;

    /**
     * Fecha de finalización
     */
    @JsonProperty("dateEnd")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "DATE_END", nullable = false, updatable = false)
    private Date dateEnd;
}
