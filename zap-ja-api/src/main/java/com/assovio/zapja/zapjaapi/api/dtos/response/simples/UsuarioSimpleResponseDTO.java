package com.assovio.zapja.zapjaapi.api.dtos.response.simples;


import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumRoleUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSimpleResponseDTO {

    private Long id;

    private String nome;

    private EnumRoleUsuario role;


}
