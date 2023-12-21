package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoCampoCustomizadoRequestDTO {

    @NotBlank
    @JsonProperty("valor")
    private String valor;

    @NotNull
    @JsonProperty("campo_customizado_id")
    private Long campoCustomizadoId;

}
