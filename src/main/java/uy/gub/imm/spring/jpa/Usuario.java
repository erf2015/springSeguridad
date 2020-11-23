package uy.gub.imm.spring.jpa;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import uy.gub.imm.spring.jpa.validators.annotations.ComparaContrasennaAnotacion;

@Entity
@Table(name = "USER")
@ComparaContrasennaAnotacion(confirmacion = "confirm", password = "password")//, message = "No-------------------------"
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@NotBlank(message = "El email es requerido")
	private String username;

	@Column
	@NotBlank(message = "El nombre es requerido")
	private String nombre;

	@Column
	@NotBlank(message = "El apellido es requerido")
	private String apellido;

	@Column
	@NotBlank(message = "La contraseña es requerida")
	@Size(min = 8, max = 100, message = "La contraseña debe tener mínimo 8 caracteres máximo 100 caractéres")
	private String password;

	@Column
	private String descripcion;

	@Column
	@Temporal(TemporalType.DATE)
	private Date fechaAlta;

	@Column
	@Temporal(TemporalType.DATE)
	private Date fechaBaja;

	@Transient
	private String confirm;

	@Transient
	private Long idRol;

	@Transient
	private boolean hasError;

	@Column
	private boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "roles_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> authority;

	public Usuario() {
		super();
	}

	public Usuario(@NotBlank(message = "El email es requerido") String username,
			@NotBlank(message = "El nombre es requerido") String nombre,
			@NotBlank(message = "El apellido es requerido") String apellido,
			@NotBlank(message = "La contraseña es requerida") @Size(min = 8, max = 100, message = "La contraseña debe tener mínimo 8 caracteres máximo 100 caractéres") String password,
			String descripcion, String confirm, boolean enabled) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.descripcion = descripcion;
		this.confirm = confirm;
		this.enabled = enabled;
	}

	// Getters y Setters

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Rol> getAuthority() {
		return authority;
	}

	public void setAuthority(Set<Rol> authority) {
		this.authority = authority;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", password=" + password + ", descripcion=" + descripcion + ", fechaAlta=" + fechaAlta
				+ ", fechaBaja=" + fechaBaja + ", confirm=" + confirm + ", idRol=" + idRol + ", hasError=" + hasError
				+ ", enabled=" + enabled + ", authority=" + authority + "]";
	}

}
