package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoCampoCustomizadoRequestDTO {

    @JsonProperty("uuid")
    private String uuid;

    @NotNull
    @JsonProperty("valor")
    private String valor;

    @NotNull
    @JsonProperty("campo_customizado_uuid")
    private String campoCustomizadoUuid;

    @NotNull
    @JsonProperty("contato_uuid")
    private String contatoUuid;

}
