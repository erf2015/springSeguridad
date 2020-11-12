package uy.gub.imm.spring.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sun.istack.logging.Logger;

import uy.gub.imm.spring.jpa.Rol;
import uy.gub.imm.spring.jpa.Usuario;
import uy.gub.imm.spring.repositorios.UsuarioRepositorio;

@Service
public class ServicioUsuarioImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio userCrud;

	private static final Logger logger = Logger.getLogger(ServicioUsuarioImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario user = userCrud.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));
		List<GrantedAuthority> roles = new ArrayList<>();
		for (Rol rol : user.getAuthority()) {
			logger.info("El rol del usuario " + username + " es: " + rol.getAuthority());
			// System.out.println("El rol del usuario " + username + " es: " +
			// rol.getAuthority());
			GrantedAuthority grant = new SimpleGrantedAuthority(rol.getAuthority());
			roles.add(grant);
		}
		UserDetails userDetails = (UserDetails) new User(user.getUsername(), user.getPassword(), roles);
		return userDetails;
	}

}
