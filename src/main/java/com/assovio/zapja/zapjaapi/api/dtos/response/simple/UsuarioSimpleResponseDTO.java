package com.assovio.zapja.zapjaapi.api.dtos.response.simple;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumPerfilUsuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSimpleResponseDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("perfil")
    private EnumPerfilUsuario perfil;
}
