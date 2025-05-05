package com.assovio.zapja.zapjaapi.api.dtos.response.simples;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumPerfilUsuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSimpleResponseDTO {

    private Long id;

    private String nome;

    private EnumPerfilUsuario perfil;
}
