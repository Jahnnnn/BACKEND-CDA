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
@Table(name = "contacto")
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {

    @Id
    @JsonProperty("idContacto")
    @Column(name = "ID_CONTACTO", nullable = false)
    private Long idContacto;

    @JsonProperty("direccionResidencia")
    @Column(name = "DIRECCION_RESIDENCIA", nullable = false)
    private String direccionResidencia;

    @JsonProperty("telefonoCelular")
    @Column(name = "TELEFONO_CELULAR", nullable = false)
    private Long telefonoCelular;

    @JsonProperty("correoElectronico")
    @Column(name = "CORREO_ELECTRONICO", nullable = false)
    private String correoElectronico;

}
