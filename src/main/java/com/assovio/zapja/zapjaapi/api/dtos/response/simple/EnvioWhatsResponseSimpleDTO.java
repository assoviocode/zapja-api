package com.assovio.zapja.zapjaapi.api.dtos.response.simple;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioWhatsResponseSimpleDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("numero_whats")
    private String numeroWhats;

    @JsonProperty("nome_contato")
    private String nomeContato;

    @JsonProperty("nome_template")
    private String nomeTemplate;

    @JsonProperty("numero_origem")
    private String numeroOrigem;

    @JsonProperty("status")
    private EnumStatusEnvioWhats status;

}
