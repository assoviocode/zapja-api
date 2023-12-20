package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateWhatsRequestDTO {

    @NotBlank
    @JsonProperty("nome")
    private String nome;

    @NotBlank
    @JsonProperty("texto")
    private String texto;

    @JsonProperty("ativo")
    private Boolean ativo;

    @JsonProperty("path_arquivo")
    private String pathArquivo;

}
