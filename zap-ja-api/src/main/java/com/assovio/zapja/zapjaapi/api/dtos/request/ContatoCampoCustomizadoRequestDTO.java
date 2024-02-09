package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoCampoCustomizadoRequestDTO {

    @JsonProperty("id")
    private Long id;

    @NotBlank
    @JsonProperty("valor")
    private String valor;

    @NotNull
    @JsonProperty("campo_customizado_id")
    private Long campoCustomizadoId;

    @NotNull
    @JsonProperty("contato_id")
    private Long contatoId;

}
