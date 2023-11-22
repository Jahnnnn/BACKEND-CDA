package com.cda.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BloqueDto {

    private String id;
    private Long numeroDeBloque;

    public BloqueDto(String id, Long numeroDeBloque) {
        this.id = id;
        this.numeroDeBloque = numeroDeBloque;
    }

    public BloqueDto() {
    }
}