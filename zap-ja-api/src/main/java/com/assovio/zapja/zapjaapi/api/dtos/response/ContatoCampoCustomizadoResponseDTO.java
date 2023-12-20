package com.assovio.zapja.zapjaapi.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoCampoCustomizadoResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("valor")
    private String valor;

    @JsonProperty("campo_customizado")
    private CampoCustomizadoResponseDTO campoCustomizadoResponseDTO;

}
