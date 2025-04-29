package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumPerfilUsuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(@NotBlank(message = "Nome é obrigatório.") String nome,
        @Size(min = 6, max = 60, message = "Email precisa ter entre 6 e 60 caracteres") @Email String email,
        @NotBlank(message = "Login é obrigatório.") String login,

        @Size(min = 3, max = 60, message = "A senha deve ter entre 3 a 60") @Pattern(regexp = "^[^;:<>'`\"]*$", message = "A senha não pode conter caracteres especiais: ;:<>'`\"") @NotBlank(message = "Senha é obrigatória.") String senha,

        @JsonProperty("cliente_id") @NotNull(message = "Para se cadastrar é necesário informar o cliente.") Long clienteId,
        @NotNull(message = "Perfil é obrigatório.") EnumPerfilUsuario perfil) {
}
