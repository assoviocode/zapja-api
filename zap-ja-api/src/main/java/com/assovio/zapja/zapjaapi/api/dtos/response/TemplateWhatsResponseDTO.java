package com.assovio.zapja.zapjaapi.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateWhatsResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("chave")
    private String chave;

    @JsonProperty("ativo")
    private Boolean ativo;

    @JsonProperty("texto")
    private String texto;

    @JsonProperty("path_arquivo")
    private String pathArquivo;

}
