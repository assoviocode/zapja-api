package com.assovio.zapja.zapjaapi.api.dtos.response;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetupEnvioWhatsResponseDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("data_prevista_inicio")
    private OffsetDateTime dataPrevistaInicio;

    @JsonProperty("intervalo_entre_mensagem_min")
    private Integer intervaloEntreMensagemMin;

    @JsonProperty("intervalo_entre_mensagem_max")
    private Integer intervaloEntreMensagemMax;

    @JsonProperty("is_recorrente")
    private Boolean isRecorrente;

    @JsonProperty("dias_recorrencia")
    private Integer diasRecorrencia;
}
