package com.assovio.zapja.zapjaapi.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampoCustomizadoResponseDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("rotulo")
    private String rotulo;

    @JsonProperty("ativo")
    private Boolean ativo;

    @JsonProperty("obrigatorio")
    private Boolean obrigatorio;

    @JsonProperty("tipo_campo_customizado")
    private TipoCampoCustomizadoResponseDTO tipoCampoCustomizado;

}
