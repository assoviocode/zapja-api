package com.assovio.zapja.zapjaapi.api.dtos.request;

import java.util.List;

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
    @JsonProperty("template_whats_uuid")
    private String templateWhatsUuid;

    @NotNull
    @JsonProperty("contatos_uuid")
    private List<String> contatosUuid;

}
