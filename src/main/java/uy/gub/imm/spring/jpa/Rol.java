package uy.gub.imm.spring.jpa;

import javax.persistence.*;

@Entity
@Table(name = "ROL")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String authority;

	public Rol() {
		// TODO Auto-generated constructor stub
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
