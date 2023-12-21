package com.assovio.zapja.zapjaapi.api.dtos.response;

import java.util.List;

import com.assovio.zapja.zapjaapi.api.dtos.response.simples.ContatoCampoCustomizadoResponseSimpleDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("numero_whats")
    private String numeroWhats;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("campos_customizados")
    private List<ContatoCampoCustomizadoResponseSimpleDTO> contatoCampoCustomizadoResponseSimpleDTOs;

}
