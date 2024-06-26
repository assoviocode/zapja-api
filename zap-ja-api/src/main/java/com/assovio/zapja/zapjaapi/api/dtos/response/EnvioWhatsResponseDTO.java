package com.assovio.zapja.zapjaapi.api.dtos.response;

import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioWhatsResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("celular_origem")
    private String celularOrigem;

    @JsonProperty("status")
    private EnumStatusEnvioWhats status;

    @JsonProperty("log")
    private String log;

    @JsonProperty("servidor")
    private String servidor;

    @JsonProperty("mensagem_final")
    private String mensagemFinal;

    @JsonProperty("template_whats")
    private TemplateWhatsResponseDTO templateWhatsResponseDTO;

    @JsonProperty("contato")
    private ContatoResponseDTO contatoResponseDTO;

}
