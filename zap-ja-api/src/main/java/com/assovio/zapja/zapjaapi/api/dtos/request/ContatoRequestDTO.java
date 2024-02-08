package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoRequestDTO {

    @NotBlank
    @JsonProperty("numero_whats")
    private String numeroWhats;

    @NotBlank
    @JsonProperty("nome")
    private String nome;
}
