package com.assovio.zapja.zapjaapi.domain.exception;

public class NaoAutorizadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NaoAutorizadoException(String message) {
		super(message);
	}

}