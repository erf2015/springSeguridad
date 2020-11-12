package uy.gub.imm.spring.utiles;

import java.time.LocalDateTime;

public class DetalleError {

	private String detalle;
	private LocalDateTime hora;
	private String mensaje;

	public DetalleError(String detalle, LocalDateTime hora, String mensaje) {
		super();
		this.detalle = detalle;
		this.hora = hora;
		this.mensaje = mensaje;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public LocalDateTime getHora() {
		return hora;
	}

	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "DetalleError [detalle=" + detalle + ", hora=" + hora + ", mensaje=" + mensaje + "]";
	}

}
