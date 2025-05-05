package com.assovio.zapja.zapjaapi.domain.exception;

public class ConflictOperationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConflictOperationException(String message) {
        super(message);
    }
}
