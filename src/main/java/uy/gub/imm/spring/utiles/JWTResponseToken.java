package uy.gub.imm.spring.utiles;

public class JWTResponseToken {

	private String token;

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

}
