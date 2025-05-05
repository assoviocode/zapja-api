package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateWhatsRequestDTO {

    @NotBlank
    @JsonProperty("nome")
    private String nome;

    @JsonProperty("chave")
    private String chave;

    @JsonProperty("ativo")
    private Boolean ativo;
}
