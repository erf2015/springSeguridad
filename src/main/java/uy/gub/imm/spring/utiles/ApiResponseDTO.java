package uy.gub.imm.spring.utiles;

import java.util.Date;

public class ApiResponseDTO {

	private String requestUrl;

	private Date fechaRequest;

	private Integer status;

	private Object authorities;

	private Object datos;

	public ApiResponseDTO(String requestUrl, Object authorities, Date fechaRequest, Object datos, Integer statusCode) {
		super();
		this.requestUrl = requestUrl;
		this.authorities = authorities;
		this.fechaRequest = fechaRequest;
		this.datos = datos;
		this.status = statusCode;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Date getFechaRequest() {
		return fechaRequest;
	}

	public void setFechaRequest(Date fechaRequest) {
		this.fechaRequest = fechaRequest;
	}

	public Object getDatos() {
		return datos;
	}

	public void setDatos(Object datos) {
		this.datos = datos;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Object authorities) {
		this.authorities = authorities;
	}

}
