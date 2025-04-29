package com.assovio.zapja.zapjaapi.api.dtos.request;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetupEnvioWhatsRequestDTO {

    @NotNull
    @JsonProperty("data_prevista_inicio")
    private OffsetDateTime dataPrevistaInicio;

    @NotNull
    @JsonProperty("intervalo_entre_mensagem")
    private Integer intervaloEntreMensagem;

    @JsonProperty("is_recorrente")
    private Boolean isRecorrente;

    @JsonProperty("dias_recorrencia")
    private Integer diasRecorrencia;

    @JsonProperty("envio_whats_base")
    private EnvioWhatsRequestDTO envioWhatsBase;
}
