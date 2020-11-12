package uy.gub.imm.spring.controller.rest;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import uy.gub.imm.spring.excepciones.EntityNotFound;
import uy.gub.imm.spring.utiles.DetalleError;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// Excepciones de negocio que puede lanzar la aplicaci'on

	@ExceptionHandler(value = { DataException.class })
	public ResponseEntity<Object> dataException(Exception ex, WebRequest request) {
		logger.info("Error dataException " + ex.getMessage());
		DetalleError error = new DetalleError(request.getDescription(true), LocalDateTime.now(),
				"Regla de negocio: " + ex.getMessage());
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class, EntityNotFound.class })
	public ResponseEntity<Object> notFound(Exception ex, WebRequest request) {
		logger.info("Error notFound " + ex.getMessage());
		DetalleError error = new DetalleError(request.getDescription(true), LocalDateTime.now(), ex.getMessage());
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	// Excepciones de errores en la invocaci'on a un endPoint rest

}
