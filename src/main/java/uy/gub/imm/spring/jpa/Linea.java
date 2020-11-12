package uy.gub.imm.spring.jpa;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LINEA")
@NamedQueries({ @NamedQuery(name = "Lineas.byName", query = "SELECT l FROM Linea l WHERE l.nombre = :nombre") })
public class Linea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@OneToOne
	@JoinColumn(name = "CODIGO_SUBSISTEMA", referencedColumnName = "ID")
	private Subsistema subsistema;

	@OneToMany(mappedBy = "linea")
	private List<SubLinea> sublineas;

	@Column
	private LocalDate fechaVigencia;

	@Column
	private LocalDate publicableWebDesde;

	@Column
	private LocalDate publicableWebHasta;

	@Column
	private LocalDate fechaBaja;

	@OneToOne
	@JoinColumn(name = "CODIGO_TIPO_LINEA", referencedColumnName = "ID")
	private TipoLinea tipoLinea;

	@OneToOne
	@JoinColumn(name = "CODIGO_INSTITUCION", referencedColumnName = "ID")
	private Institucion empresa;

	@Column
	private String tarifaXKm;

	public Linea() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<SubLinea> getSublineas() {
		return sublineas;
	}

	public void setSublineas(List<SubLinea> sublineas) {
		this.sublineas = sublineas;
	}

	public TipoLinea getTipoLinea() {
		return tipoLinea;
	}

	public void setTipoLinea(TipoLinea tipoLinea) {
		this.tipoLinea = tipoLinea;
	}

	public Subsistema getSubsistema() {
		return subsistema;
	}

	public void setSubsistema(Subsistema subsistema) {
		this.subsistema = subsistema;
	}

	public LocalDate getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(LocalDate fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public LocalDate getPublicableWebDesde() {
		return publicableWebDesde;
	}

	public void setPublicableWebDesde(LocalDate publicableWebDesde) {
		this.publicableWebDesde = publicableWebDesde;
	}

	public LocalDate getPublicableWebHasta() {
		return publicableWebHasta;
	}

	public void setPublicableWebHasta(LocalDate publicableWebHasta) {
		this.publicableWebHasta = publicableWebHasta;
	}

	public LocalDate getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getTarifaXKm() {
		return tarifaXKm;
	}

	public void setTarifaXKm(String tarifaXKm) {
		this.tarifaXKm = tarifaXKm;
	}

	public Institucion getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Institucion empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "Linea [id=" + id + ", nombre=" + nombre + ", subsistema=" + subsistema + ", sublineas=" + sublineas
				+ ", fechaVigencia=" + fechaVigencia + ", publicableWebDesde=" + publicableWebDesde
				+ ", publicableWebHasta=" + publicableWebHasta + ", fechaBaja=" + fechaBaja + ", tipoLinea=" + tipoLinea
				+ ", empresa=" + empresa + ", tarifaXKm=" + tarifaXKm + "]";
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
		Linea other = (Linea) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
