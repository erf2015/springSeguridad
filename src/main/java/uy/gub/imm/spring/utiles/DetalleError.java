package uy.gub.imm.spring.utiles;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class DetalleError {

	private HttpStatus status;
	private List<String> mensaje;
	private LocalDateTime hora;
	private String debugMessage;

	public DetalleError(HttpStatus status, List<String> errores, LocalDateTime hora, String mensaje) {
		super();
		this.status = status;
		this.mensaje = errores;
		this.hora = hora;
		this.debugMessage = mensaje;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<String> getMensaje() {
		return mensaje;
	}

	public void setMensaje(List<String> mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getHora() {
		return hora;
	}

	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	@Override
	public String toString() {
		return "DetalleError [status=" + status + ", mensaje=" + mensaje + ", hora=" + hora + ", debugMessage="
				+ debugMessage + "]";
	}

}
