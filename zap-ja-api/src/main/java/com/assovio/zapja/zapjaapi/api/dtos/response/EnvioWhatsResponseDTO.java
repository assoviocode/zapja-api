package com.assovio.zapja.zapjaapi.api.dtos.response;

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

    @JsonProperty("enviado")
    private Boolean enviado;

    @JsonProperty("log")
    private String log;

    @JsonProperty("servidor")
    private String servidor;

    @JsonProperty("template_whats")
    private TemplateWhatsResponseDTO templateWhatsResponseDTO;

    @JsonProperty("contato")
    private ContatoResponseDTO contatoResponseDTO;

}
