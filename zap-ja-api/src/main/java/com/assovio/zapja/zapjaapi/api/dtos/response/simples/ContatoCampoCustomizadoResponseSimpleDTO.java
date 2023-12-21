package com.assovio.zapja.zapjaapi.api.dtos.response.simples;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoCampoCustomizadoResponseSimpleDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("valor")
    private String valor;

    @JsonProperty("campo_customizado_id")
    private Long campoCustomizadoId;

    @JsonProperty("rotulo")
    private String rotulo;

    @JsonProperty("mascara")
    private String mascara;

}
