package uy.gub.imm.spring.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LINEA_EMPRESA")
public class LineaEmpresa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LineaEmpresaPK pk;

	@Column
	private LocalDateTime fechaAlta;

	@Column
	private LocalDateTime fechaBaja;

	public LineaEmpresa(LocalDateTime fechaAlta, LocalDateTime fechaBaja) {
		super();
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
	}

	public LineaEmpresa() {
		super();
	}

	public LineaEmpresaPK getPk() {
		return pk;
	}

	public void setPk(LineaEmpresaPK pk) {
		this.pk = pk;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDateTime getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
		LineaEmpresa other = (LineaEmpresa) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

}
