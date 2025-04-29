package com.assovio.zapja.zapjaapi.api.dtos.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateWhatsRequestDTO {

    @NotBlank
    @JsonProperty("nome")
    private String nome;

    @JsonProperty("chave")
    private String chave;

    @JsonProperty("ativo")
    private Boolean ativo;

    @NotNull
    @NotEmpty
    @JsonProperty("mensagens_whats")
    private List<MensagemWhatsRequestDTO> mensagensWhats;

}
