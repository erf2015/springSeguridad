package uy.gub.imm.spring.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uy.gub.imm.spring.servicios.ServicioUsuarioImpl;
import uy.gub.imm.spring.utiles.JWTRequestToken;
import uy.gub.imm.spring.utiles.JWTResponseToken;
import uy.gub.imm.spring.utiles.JwtUtils;

@RestController
@RequestMapping(path = "/jwt")
public class JWTRestController {

	private static final Logger logger = LoggerFactory.getLogger(JWTRestController.class);

	@Autowired
	private AuthenticationManager auth;

	@Autowired
	private JwtUtils jwt;

	@Autowired
	private ServicioUsuarioImpl userDetailService;

	@RequestMapping(path = "/home")
	public ResponseEntity<Object> home() {
		logger.info("API works");
		return ResponseEntity.ok().body("API works");
	}

	@RequestMapping(path = "/authenticar", method = RequestMethod.POST)
	public ResponseEntity<Object> validarToken(@RequestBody JWTRequestToken request) {
		/*
		try {
			auth.authenticate(new UsernamePasswordAuthenticationToken(request.getNombre(), request.getPassword()));		
		} catch (Exception e) {
			System.out.println("Error de authenticación " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe en la base.");
		}
		*/
		try {
			UserDetails userdetails = userDetailService.loadUserByUsername(request.getNombre());
			String token = jwt.generarToken(userdetails);
			JWTResponseToken response = new JWTResponseToken(token);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe en la base.");
		}
	}

}
