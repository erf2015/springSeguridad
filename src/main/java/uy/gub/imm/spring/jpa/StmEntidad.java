package uy.gub.imm.spring.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class StmEntidad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "FECHA_BAJA")
	@Temporal(TemporalType.DATE)
	private Date fechaBaja;

	@Column(name = "SUBSISTEMA")
	private Integer subsistema;

	public StmEntidad() {
		super();
	}

	public StmEntidad(Long id, String descripcion, Date fechaBaja, Integer subsistema) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.fechaBaja = fechaBaja;
		this.subsistema = subsistema;
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
		StmEntidad other = (StmEntidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StmEntidad [id=" + id + ", descripcion=" + descripcion + ", fechaBaja=" + fechaBaja + ", subsistema="
				+ subsistema + "]";
	}

}
