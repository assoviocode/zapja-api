package com.assovio.zapja.zapjaapi.api.dtos.response.simple;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemWhatsResponseSimpleDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("ordem_envio")
    private Integer ordemEnvio;

    @JsonProperty("texto")
    private String texto;

}
