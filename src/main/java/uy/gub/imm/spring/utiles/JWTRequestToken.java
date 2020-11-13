package uy.gub.imm.spring.utiles;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.NonNull;

public class JWTRequestToken {

	@Column
	@NotBlank(message = "El email es requerido")
	private String username;

	@Column
	@NotBlank(message = "El nombre es requerido")
	private String nombre;

	@Column
	@NotBlank(message = "El apellido es requerido")
	private String apellido;

	@NonNull
	@Size(min = 3, message = "La contraseña debe tener al menos 3 carateres")
	private String password;

	public JWTRequestToken(String nombre, String password) {
		super();
		this.nombre = nombre;
		this.password = password;
	}

	public JWTRequestToken() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return "JWTRequestToken [username=" + username + ", nombre=" + nombre + ", apellido=" + apellido + ", password="
				+ password + "]";
	}

}
