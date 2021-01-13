package uy.gub.imm.spring.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import uy.gub.imm.spring.excepciones.DatoInvalidoException;
import uy.gub.imm.spring.excepciones.EntityNotFound;
import uy.gub.imm.spring.excepciones.ErrorInternoException;
import uy.gub.imm.spring.utiles.DetalleError;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// Excepciones de negocio que puede lanzar la aplicaci'on

	@ExceptionHandler(value = { DatoInvalidoException.class })
	public ResponseEntity<Object> dataException(Exception ex, WebRequest request) {
		logger.info("@ExceptionHandler DatoInvalidoException " + ex.getMessage());
		List<String> erroresList = new ArrayList<>();
		erroresList.add(ex.getMessage());
		erroresList.add(request.getDescription(true));
		DetalleError error = new DetalleError(HttpStatus.CONFLICT, erroresList, LocalDateTime.now(),
				"Regla de negocio: " + ex.getMessage());
		return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class, EntityNotFound.class })
	public ResponseEntity<Object> notFound(Exception ex, WebRequest request) {
		logger.info("@ExceptionHandler EntityNotFoundException " + ex.getMessage());
		List<String> erroresList = new ArrayList<>();
		erroresList.add(ex.getMessage());
		erroresList.add(request.getDescription(true));
		DetalleError error = new DetalleError(HttpStatus.NOT_FOUND, erroresList, LocalDateTime.now(), ex.getMessage());
		return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
	}

	@ExceptionHandler(value = { ErrorInternoException.class })
	public ResponseEntity<Object> errorInterno(Exception ex, WebRequest request) {
		logger.info("@ExceptionHandler ErrorInternoException " + ex.getMessage());
		List<String> erroresList = new ArrayList<>();
		erroresList.add(ex.getMessage());
		erroresList.add(request.getDescription(true));
		DetalleError error = new DetalleError(HttpStatus.INTERNAL_SERVER_ERROR, erroresList, LocalDateTime.now(),
				ex.getMessage());
		return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
	}

	/**
	 * Excepciones de errores en la invocaci'on a un endPoint rest
	 */

	/**
	 * Controla los errores provocados por la anotación @Valid en una entidad que se
	 * pasa como @RequestBody cuando salta algún control de las validaciones de un
	 * campo, estos errores listan y se envían en el request para que el usuario
	 * sepa en que fallo.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("handleMethodArgumentNotValid  " + ex.getMessage());
		List<String> erroresList = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
		DetalleError error = new DetalleError(HttpStatus.BAD_REQUEST, erroresList, LocalDateTime.now(),
				ex.getMessage());
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	/**
	 * Tratando de capturar los errores relacionados con un mal formato delos
	 * parámetros de Request
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("handleHttpMessageNotReadable BAD_REQUEST " + ex.getMessage());
		List<String> errorList = new ArrayList<>();
		errorList.add("Ocurrió un error al parsear la estructura del objeto enviado.");
		DetalleError error = new DetalleError(HttpStatus.BAD_REQUEST, errorList, LocalDateTime.now(), ex.getMessage());
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex,
			HttpServletResponse response) {
		logger.info("AuthenticationException " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{ \"error\": \"" + ex.getMessage() + "\" }");
	}

	/*
	 * @ExceptionHandler(AccessDeniedException.class) public ResponseEntity<Object>
	 * handleAccessDeniedException(AccessDeniedException ex, HttpServletResponse
	 * response) { logger.info("AccessDeniedException " + ex.getMessage()); return
	 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{ \"error\": \"" +
	 * ex.getMessage() + "\" }"); }
	 */
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		logger.info("AccessDeniedException " + ex.getMessage());
		return new ResponseEntity<Object>("{ \"error\": \"" + ex.getMessage() + "\" }", new HttpHeaders(),
				HttpStatus.FORBIDDEN);
	}

}
