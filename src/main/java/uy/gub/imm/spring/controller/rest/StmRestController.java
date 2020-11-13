package uy.gub.imm.spring.controller.rest;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import uy.gub.imm.spring.jpa.StmEntidad;
import uy.gub.imm.spring.repositorios.StmEntidadServicio;

@Controller
@RequestMapping(path = "/servicio")
public class StmRestController {

	private static final Logger logger = LoggerFactory.getLogger(StmRestController.class);

	@Autowired
	private StmEntidadServicio servicio;

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
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public ResponseEntity<Object> entidades() {
		Iterable<StmEntidad> resultado = servicio.findAll();
		return ResponseEntity.ok().body(resultado);
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

}
