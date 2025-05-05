package com.assovio.zapja.zapjaapi.api.exceptions.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Problema {

	private Integer status;
	private OffsetDateTime dataHora;
	private String titulo;
	private List<Campo> campos;

	public static class Campo {
		private final String nome;
		private final String mensagem;

		public Campo(String nome, String mensagem) {
			this.nome = nome;
			this.mensagem = mensagem;
		}

		public String getNome() {
			return nome;
		}

		public String getMensagem() {
			return mensagem;
		}

	}
}