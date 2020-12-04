package uy.gub.imm.spring.controller.rest;

import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uy.gub.imm.spring.jpa.Rol;
import uy.gub.imm.spring.jpa.Usuario;
import uy.gub.imm.spring.repositorios.RolRepositorio;
import uy.gub.imm.spring.repositorios.UsuarioRepositorio;
import uy.gub.imm.spring.servicios.ServicioUsuarioImpl;
import uy.gub.imm.spring.utiles.ApiResponseDTO;
import uy.gub.imm.spring.utiles.JWTRequestToken;
import uy.gub.imm.spring.utiles.JWTResponseToken;
import uy.gub.imm.spring.utiles.JwtUtils;

@RestController
@RequestMapping(path = "/jwt")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class JWTRestController {

	private static final Logger logger = LoggerFactory.getLogger(JWTRestController.class);

	@Autowired
	private AuthenticationManager auth;

	@Autowired
	private JwtUtils jwt;

	@Autowired
	private ServicioUsuarioImpl userDetailService;

	@Autowired
	private UsuarioRepositorio repoUser;

	@Autowired
	private RolRepositorio repoRol;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private PasswordEncoder encoder;

	@RequestMapping(path = "/home")
	public ResponseEntity<Object> home() {
		logger.info("API works");
		return ResponseEntity.ok().body("API works");
	}

	@RequestMapping(path = "/authenticar", method = RequestMethod.POST)
	public ResponseEntity<Object> validarToken(@Valid @RequestBody JWTRequestToken request) {
		try {
			Authentication authentication = auth.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userdetails = userDetailService.loadUserByUsername(request.getUsername());
			String token = jwt.generarToken(userdetails);
			JWTResponseToken response = new JWTResponseToken(token);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			logger.info("Error en  validarToken: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe en la base.");
		}
	}

	@RequestMapping(path = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> nuevoUser(@Valid @RequestBody JWTRequestToken user) {
		logger.info("Inicio nuevoUser: " + user);
		ApiResponseDTO response;
		if (user.getNombre() == null || user.getPassword() == null) {
			response = new ApiResponseDTO(request.getRequestURL().toString(), null, new Date(),
					"El nombre o las credenciales son requeridas " + user, 400);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		Usuario nuevo = repoUser.findByUsername(user.getUsername()).orElse(null);
		if (nuevo == null) {
			Rol rol = repoRol.findByAuthority("USER").orElse(null);
			nuevo = new Usuario();
			nuevo.setUsername(user.getUsername());
			nuevo.setNombre(user.getNombre());
			nuevo.setApellido(user.getApellido());
			nuevo.setPassword(encoder.encode(user.getPassword()));
			nuevo.setAuthority(new HashSet<>());
			nuevo.getAuthority().add(rol);
			nuevo = repoUser.save(nuevo);
			response = new ApiResponseDTO(request.getRequestURL().toString(), nuevo.getAuthority(), new Date(), nuevo,
					200);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			response = new ApiResponseDTO(request.getRequestURL().toString(), null, new Date(),
					"El nombre de usuario ya existe " + user, 400);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
