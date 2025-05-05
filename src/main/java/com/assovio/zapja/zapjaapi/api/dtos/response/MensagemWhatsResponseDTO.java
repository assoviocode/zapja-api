package com.assovio.zapja.zapjaapi.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemWhatsResponseDTO {

    @JsonProperty("ordem_envio")
    private Integer ordemEnvio;

    @JsonProperty("texto")
    private String texto;

    @JsonProperty("midia")
    private String midia;

}
