package uy.gub.imm.spring.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LineaEmpresaPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private Long idInstitucion;

	@Column
	private Long idLinea;

	public LineaEmpresaPK(Long idInstitucion, Long idLinea) {
		super();
		this.idInstitucion = idInstitucion;
		this.idLinea = idLinea;
	}

	public LineaEmpresaPK() {
		super();
	}

	public Long getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idLinea == null) ? 0 : idLinea.hashCode());
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
		LineaEmpresaPK other = (LineaEmpresaPK) obj;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idLinea == null) {
			if (other.idLinea != null)
				return false;
		} else if (!idLinea.equals(other.idLinea))
			return false;
		return true;
	}

}
