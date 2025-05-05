package com.assovio.zapja.zapjaapi.domain.exceptions;

public class NaoAutorizadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NaoAutorizadoException(String message) {
		super(message);
	}

}