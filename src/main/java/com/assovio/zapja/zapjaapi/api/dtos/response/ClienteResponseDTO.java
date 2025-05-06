package com.assovio.zapja.zapjaapi.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("nome")
    private String nome;
}
