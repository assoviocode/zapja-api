package com.assovio.zapja.zapjaapi.api.exceptions.handlers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assovio.zapja.zapjaapi.domain.exception.ConflictOperationException;
import com.assovio.zapja.zapjaapi.domain.exception.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exception.NaoAutorizadoException;
import com.assovio.zapja.zapjaapi.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
			@NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

		List<Problema.Campo> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> new Problema.Campo(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());

		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Falha na validação");
		problema.setCampos(errors);

		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHandlerMethodValidationException(
			@NonNull HandlerMethodValidationException ex,
			@NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
		status = HttpStatus.BAD_REQUEST;

		List<Problema.Campo> errors = ex.getAllErrors()
				.stream()
				.filter(error -> error instanceof FieldError)
				.map(error -> new Problema.Campo(((FieldError) error).getField(), error.getDefaultMessage()))
				.collect(Collectors.toList());

		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Falha na validação");
		problema.setCampos(errors);

		return new ResponseEntity<>(problema, new HttpHeaders(), status);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Operação não concluida!");
		problema.setMensagem(ex.getMessage());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Operação inválida!");
		problema.setMensagem(ex.getMessage());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NaoAutorizadoException.class)
	public ResponseEntity<Object> handleNaoAutorizado(NaoAutorizadoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;

		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Dados inválidos!");
		problema.setMensagem(ex.getMessage());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(ConflictOperationException.class)
	public ResponseEntity<Object> handleOperationRefusedException(ConflictOperationException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaughtException(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Erro interno no servidor");
		problema.setMensagem("Ocorreu um erro inesperado. Tente novamente mais tarde.");

		ex.printStackTrace();

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

}
