package com.assovio.zapja.zapjaapi.api.dtos.response;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumPerfilUsuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

	@JsonProperty("uuid")
	private String uuid;

	private String nome;

	private String email;

	private String login;

	private EnumPerfilUsuario perfil;

	private String token;

}