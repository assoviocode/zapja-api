package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioWhatsRequestDTO {

    @NotBlank
    @JsonProperty("celular_origem")
    private String celularOrigem;

    @NotNull
    @JsonProperty("template_whats_id")
    private Long templateWhatsId;

    @JsonProperty("enviado")
    private Boolean enviado;

    @NotNull
    @JsonProperty("contato_id")
    private Long contatoId;

}
