package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemWhatsRequestDTO {

    @NotNull
    @JsonProperty("ordem_envio")
    private Integer ordemEnvio;

    @JsonProperty("texto")
    private String texto;

    @JsonProperty("midia")
    private String midia;

}
