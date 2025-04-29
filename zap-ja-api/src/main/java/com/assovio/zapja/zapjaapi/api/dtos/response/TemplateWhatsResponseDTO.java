package com.assovio.zapja.zapjaapi.api.dtos.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateWhatsResponseDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("chave")
    private String chave;

    @JsonProperty("ativo")
    private Boolean ativo;

    @JsonProperty("mensagens_whats")
    private List<MensagemWhatsResponseDTO> mensagensWhats;
}
