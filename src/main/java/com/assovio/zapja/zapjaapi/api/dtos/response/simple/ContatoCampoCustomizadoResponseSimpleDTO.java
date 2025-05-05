package com.assovio.zapja.zapjaapi.api.dtos.response.simple;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoCampoCustomizadoResponseSimpleDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("valor")
    private String valor;

    @JsonProperty("campo_customizado_id")
    private Long campoCustomizadoId;

    @JsonProperty("rotulo")
    private String rotulo;

    @JsonProperty("mascara")
    private String mascara;

}
