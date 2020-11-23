package uy.gub.imm.spring.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.gub.imm.spring.repositorios.UsuarioRepositorio;

@Service
public class CampoUnicoDelObjetoImpl implements CampoUnicoDelObjetoExtends {

	@Autowired
	private UsuarioRepositorio repoUsuario;

	@Override
	public boolean existeValorEnEntidad(Object valor, String campo) throws UnsupportedOperationException {
		if (!campo.equals("email")) {
			throw new UnsupportedOperationException("Field name not supported");
		}
		if (valor == null) {
			return false;
		}
		return this.repoUsuario.findByUsername(valor.toString()).isPresent();
	}

}
