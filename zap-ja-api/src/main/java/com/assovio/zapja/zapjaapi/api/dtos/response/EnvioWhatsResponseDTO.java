package com.assovio.zapja.zapjaapi.api.dtos.response;

import java.util.List;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioWhatsResponseDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("celular_origem")
    private String celularOrigem;

    @JsonProperty("celular_destino")
    private String celularDestino;

    @JsonProperty("status")
    private EnumStatusEnvioWhats status;

    @JsonProperty("log")
    private String log;

    @JsonProperty("mensagens_tratadas")
    private List<MensagemWhatsResponseDTO> mensagensTratadas;

    @JsonProperty("setup_envio_whats")
    private SetupEnvioWhatsResponseDTO setupEnvioWhats;

}
