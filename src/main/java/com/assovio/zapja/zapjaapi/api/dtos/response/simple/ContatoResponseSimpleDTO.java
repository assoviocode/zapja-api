package com.assovio.zapja.zapjaapi.api.dtos.response.simple;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoResponseSimpleDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("numero_whats")
    private String numeroWhats;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("is_faltando_campo")
    private Boolean isFaltandoCampo;


}
