package uy.gub.imm.spring.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Ocurre un error en el sistema")
public class ErrorInternoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorInternoException(String mensaje) {
		super(mensaje);
	}

	public ErrorInternoException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
