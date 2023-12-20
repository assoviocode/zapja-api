package com.assovio.zapja.zapjaapi.api.dtos.request;

import java.util.List;

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

    @JsonProperty("campos_customizados")
    private List<Object> camposCustomizados;
}
