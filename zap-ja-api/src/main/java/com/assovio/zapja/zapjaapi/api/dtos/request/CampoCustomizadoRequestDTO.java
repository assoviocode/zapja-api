package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampoCustomizadoRequestDTO {

    @NotBlank
    @JsonProperty("rotulo")
    private String rotulo;

    @JsonProperty("ativo")
    private Boolean ativo;

    @NotNull
    @JsonProperty("tipo_campo_customizado_id")
    private Long tipoCampoCustomizadoId;

}
