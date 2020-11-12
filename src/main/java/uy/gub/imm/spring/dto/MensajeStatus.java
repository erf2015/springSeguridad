package uy.gub.imm.spring.dto;

import java.io.Serializable;

public class MensajeStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mensaje;
	
	private Integer status;

	public MensajeStatus(String mensaje, Integer status) {
		super();
		this.mensaje = mensaje;
		this.status = status;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
