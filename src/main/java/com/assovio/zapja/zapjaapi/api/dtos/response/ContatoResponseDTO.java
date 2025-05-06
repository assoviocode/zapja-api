package com.assovio.zapja.zapjaapi.api.dtos.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoResponseDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("numero_whats")
    private String numeroWhats;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("is_faltando_campo")
    private Boolean isFaltandoCampo;

    @JsonProperty("contato_campos_customizados")
    private List<ContatoCampoCustomizadoResponseDTO> contatosCamposCustomizados;

}
