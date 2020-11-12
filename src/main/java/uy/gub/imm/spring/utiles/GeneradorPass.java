package uy.gub.imm.spring.utiles;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneradorPass {

	public GeneradorPass() {
	}

	public String main(String args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		// El String que mandamos al metodo encode es el password que queremos
		// encriptar.
		System.out.println(bCryptPasswordEncoder.encode(args));
		return bCryptPasswordEncoder.encode(args);
	}
}
