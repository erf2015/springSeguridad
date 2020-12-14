package uy.gub.imm.spring.controller.rest;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uy.gub.imm.spring.jpa.StmEntidad;
import uy.gub.imm.spring.jpa.Usuario;
import uy.gub.imm.spring.repositorios.StmEntidadServicio;
import uy.gub.imm.spring.repositorios.UsuarioRepositorio;
import uy.gub.imm.spring.utiles.ApiResponseDTO;
import uy.gub.imm.spring.utiles.JwtUtils;

@Controller
@RequestMapping(path = "/servicio")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class StmRestController {

	private static final Logger logger = LoggerFactory.getLogger(StmRestController.class);

	@Autowired
	private StmEntidadServicio servicio;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JwtUtils jwt;

	@Autowired
	private UsuarioRepositorio repoUser;

	@GetMapping(path = "/get/{id}")
	@PreAuthorize(value = "hasAuthority('USER')")
	public ResponseEntity<StmEntidad> obtenerEntidadPorID(@PathVariable(name = "id", required = true) Long id)
			throws EntityNotFoundException {
		String metodo = "obtenerEntidadPorID";
		logger.debug(metodo + " Parámetro ID: ", id);
		StmEntidad ent = servicio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("La entidad con código " + id + " no existe en base."));
		return ResponseEntity.ok().body(ent);
	}

	@GetMapping(path = "/")
	@PreAuthorize(value = "hasAuthority('USER')")
	public ResponseEntity<Object> entidades() {
		Iterable<StmEntidad> resultado = servicio.findAll();
		ApiResponseDTO response = new ApiResponseDTO(request.getRequestURL().toString(),
				jwt.extraerAuthorities(request), new Date(), resultado, HttpStatus.OK.value());
		// return ResponseEntity.ok().body(resultado);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(path = "/post/")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public ResponseEntity<StmEntidad> adicionarEntidad(@Valid @RequestBody StmEntidad entidad) {
		String metodo = "adicionarEntidad";
		logger.debug(metodo + ": ", entidad.toString());
		StmEntidad resultado = servicio.save(entidad);
		logger.debug(metodo + " Entidad creada exitosamente: ", resultado.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
	}

	@PutMapping(path = "/update/")
	public ResponseEntity<StmEntidad> actualizarEntidad(@Valid @RequestBody StmEntidad entidad) {
		String metodo = "actualizarEntidad";
		logger.debug(metodo + ": ", entidad.toString());
		StmEntidad resultado = servicio.findById(entidad.getId()).orElseThrow(
				() -> new EntityNotFoundException("La entidad con código " + entidad.getId() + " no existe en base."));
		resultado.setDescripcion(entidad.getDescripcion());
		resultado.setFechaBaja(entidad.getFechaBaja());
		resultado.setSubsistema(entidad.getSubsistema());
		servicio.save(resultado);
		logger.debug(metodo + " Entidad actualizada exitosamente: ", entidad.toString());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(resultado);
	}

	@DeleteMapping(path = "/remove/{id}")
	public ResponseEntity<Object> eliminarEntidad(@PathVariable(name = "id", required = true) Long id) {
		String metodo = "eliminarEntidad";
		logger.debug(metodo + " Parámetro ID: ", id);
		StmEntidad eliminar = servicio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("La entidad con código " + id + " no existe en base."));
		servicio.delete(eliminar);
		logger.debug(metodo + " Entidad eliminada");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Entidad eliminada.");
	}

	@GetMapping(path = "/user/all")
	@PreAuthorize(value = "hasAuthority('USER')")
	public ResponseEntity<Object> allUser() {
		List<Usuario> users = repoUser.findAll();
		ApiResponseDTO response = new ApiResponseDTO(request.getRequestURL().toString(),
				jwt.extraerAuthorities(request), new Date(), users, HttpStatus.OK.value());
		// return ResponseEntity.ok().body(response);
		return ResponseEntity.ok().body(users);
	}

	@PostMapping(path = "/user/add")
	//@RequestMapping(path = "/user/add", method = RequestMethod.POST)
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public ResponseEntity<Object> adicionarUsuario(@Valid Usuario nuevo) {
		logger.info("adicionarUsuario: " + nuevo);
		Usuario verificar = repoUser.findByUsername(nuevo.getUsername()).orElse(null);
		if (verificar == null) {
			repoUser.save(nuevo);
		} else {
			logger.info("adicionarUsuario: " + "Usuario ya existe");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("El usuario con nombre " + nuevo.getUsername() + " ya existe");
		}
		logger.info("adicionarUsuario: " + "Usuario creado correctamente");
		return ResponseEntity.ok().body("Usuario creado correctamente");
	}

	@PostMapping(path = "/user/editar")
	//@RequestMapping(path = "/user/editar", method = RequestMethod.POST)
	@PreAuthorize(value = "hasAuthority('USER')")
	public ResponseEntity<Object> editarDatosUsuario(@Valid Usuario nuevo) {
		logger.info("editarDatosUsuario: " + nuevo);
		Usuario verificar = repoUser.findByUsername(nuevo.getUsername()).orElse(null);
		if (verificar != null) {
			repoUser.save(nuevo);
		} else {
			logger.info("adicionarUsuario: " + "Usuario no existe");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("El usuario con nombre " + nuevo.getUsername() + " no existe");
		}
		logger.info("adicionarUsuario: " + "Usuario editado correctamente");
		return ResponseEntity.ok().body("Usuario editado correctamente");
	}

	@DeleteMapping(path = "/user/baja")
	//@RequestMapping(path = "/user/baja", method = RequestMethod.DELETE)
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public ResponseEntity<Object> bajaUsuario(Long idUsuario) {
		logger.info("bajaUsuario: " + idUsuario);
		Usuario verificar = repoUser.findById(idUsuario).orElse(null);
		if (verificar != null) {
			repoUser.deleteById(idUsuario);
		} else {
			logger.info("adicionarUsuario: " + "Usuario no existe");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario con id " + idUsuario + " no existe");
		}
		logger.info("adicionarUsuario: " + "Usuario dado de baja correctamente");
		return ResponseEntity.ok().body("Usuario dado de baja correctamente");
	}
	@GetMapping(path = "/user/get/{id}")
	//@RequestMapping(path = "/user/get/{id}", method = RequestMethod.GET)
	@PreAuthorize(value = "hasAuthority('USER')")
	public ResponseEntity<Object> obteneUsuarioPorId(@PathVariable(name = "id") Long id) {
		logger.info("obteneUsuarioPorId " + id);
		Usuario buscar = repoUser.findById(id).orElse(null);
		if (buscar != null) {
			logger.info("Usuario  " + buscar.getNombre());
			return ResponseEntity.ok().body(buscar);
		} else {
			logger.info("El usuario con id " + id + " no existe.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no id " + id + " no existe.");
		}
	}

}
