package uy.gub.imm.spring.utiles;

public class JWTRequestToken {

	private String nombre;
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

	@Override
	public String toString() {
		return "JWTRequestToken [nombre=" + nombre + ", password=" + password + "]";
	}

}
