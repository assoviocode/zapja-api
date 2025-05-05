package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoCampoCustomizadoRequestDTO {

    @NotBlank
    @JsonProperty("nome")
    private String nome;

    @NotNull
    @JsonProperty("mascara")
    private String mascara;

}
