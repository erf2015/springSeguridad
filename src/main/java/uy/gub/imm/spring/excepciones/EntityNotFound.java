package uy.gub.imm.spring.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "El elemento no existe en el sistema")
public class EntityNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFound(String mensaje) {
		super(mensaje);
	}

	public EntityNotFound(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
