package com.assovio.zapja.zapjaapi.api.dtos.response;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumPerfilUsuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

	private String uuid;

	private String nome;

	private String email;

	private String login;

	private EnumPerfilUsuario perfil;

	private String token;

}