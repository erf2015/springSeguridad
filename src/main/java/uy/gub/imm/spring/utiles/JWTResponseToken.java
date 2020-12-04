package uy.gub.imm.spring.utiles;

import java.io.Serializable;

import uy.gub.imm.spring.jpa.Usuario;

public class JWTResponseToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;

	private Usuario user;

	public JWTResponseToken(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

}
