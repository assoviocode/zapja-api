package com.assovio.zapja.zapjaapi.api.dtos.response;

import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumRoleUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

	private Long id;
	
	private String nome;
	
	private String login;
	
	private String email;
	
	private EnumRoleUsuario role;
	
	private String token;
	
}