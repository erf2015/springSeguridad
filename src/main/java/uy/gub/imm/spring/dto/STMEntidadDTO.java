package uy.gub.imm.spring.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import uy.gub.imm.spring.jpa.StmEntidad;

public class STMEntidadDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String descripcion;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaBaja;

	private Integer subsistema;

	public STMEntidadDTO(StmEntidad entity) {
		this.id = entity.getId();
		this.descripcion = entity.getDescripcion();
		this.fechaBaja = entity.getFechaBaja();
		this.subsistema = entity.getSubsistema();
	}

	public STMEntidadDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Integer getSubsistema() {
		return subsistema;
	}

	public void setSubsistema(Integer subsistema) {
		this.subsistema = subsistema;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		STMEntidadDTO other = (STMEntidadDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "STMEntidadDTO [id=" + id + ", descripcion=" + descripcion + ", fechaBaja=" + fechaBaja + ", subsistema="
				+ subsistema + "]";
	}

}
