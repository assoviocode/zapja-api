package com.assovio.zapja.zapjaapi.api.dtos.response.simple;

import java.time.OffsetDateTime;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioWhatsResponseSimpleDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("celular_origem")
    private String celularOrigem;

    @JsonProperty("status")
    private EnumStatusEnvioWhats status;

    @JsonProperty("log")
    private String log;

    @JsonProperty("data_real")
    private OffsetDateTime dataReal;

    @JsonProperty("celular_destino")
    private String celularDestino;

    @JsonProperty("nome_contato")
    private String nomeContato;

    @JsonProperty("nome_template_whats")
    private String nomeTemplateWhats;
}
