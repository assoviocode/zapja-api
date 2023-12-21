package com.assovio.zapja.zapjaapi.api.dtos.response.simples;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoResponseSimpleDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("numero_whats")
    private String numeroWhats;

    @JsonProperty("nome")
    private String nome;

}
