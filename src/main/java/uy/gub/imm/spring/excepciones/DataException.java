package uy.gub.imm.spring.excepciones;

public class DataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String causa;

	public DataException(String msg) {
		super(msg);
	}

	public DataException(String msg, String causa) {
		super(msg);
		this.causa = causa;
	}

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

}
