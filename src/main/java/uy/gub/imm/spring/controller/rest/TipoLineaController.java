package uy.gub.imm.spring.controller.rest;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uy.gub.imm.spring.excepciones.DatoInvalidoException;
import uy.gub.imm.spring.excepciones.EntityNotFound;
import uy.gub.imm.spring.excepciones.ErrorInternoException;
import uy.gub.imm.spring.jpa.TipoLinea;
import uy.gub.imm.spring.repositorios.TipoLineaRepositorio;
import uy.gub.imm.spring.utiles.Estados;

@RestController
@RequestMapping(path = "/servicio/tipolinea", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@CrossOrigin(origins = "*")
/*
 * methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
 * RequestMethod.DELETE }
 */
public class TipoLineaController {

	private Logger logger = LoggerFactory.getLogger(TipoLineaController.class);

	@Autowired
	private TipoLineaRepositorio repoTipoLinea;

	@PostMapping(path = "/add", produces = { "application/json" })
	public ResponseEntity<Object> adicionarTipoLinea(@Valid @RequestBody TipoLinea nuevo)
			throws ErrorInternoException, DatoInvalidoException {
		String method = "adicionarTipoLinea";
		logger.info(Estados.BEGIN + " " + method);
		try {
			List<TipoLinea> tipos = repoTipoLinea.findAll();
			if (tipos != null
					&& tipos.stream().filter(tipo -> Objects.equals(tipo.getDescripcion(), nuevo.getDescripcion()))
							.findFirst().orElse(null) != null) {
				logger.info(Estados.ERROR + " " + method + " Ya existe un tipo línea con ese nombre.");
				throw new DatoInvalidoException(" Ya existe un tipo línea con ese nombre.");
			} else {
				repoTipoLinea.save(nuevo);
				logger.info(Estados.SUCCES + " " + method);
				return ResponseEntity.ok().build();
			}
		} catch (Exception e) {
			logger.info(Estados.ERROR + " " + method + " Error inesperado " + e.getMessage());
			throw new ErrorInternoException(e.getMessage());
		}
	}

	@GetMapping(path = "/all", produces = { "application/json" })
	public ResponseEntity<Object> listarTipoLineas() {
		String method = "listarTipoLineas";
		logger.info(Estados.BEGIN + " " + method);
		List<TipoLinea> tipos = repoTipoLinea.findAll();
		logger.info(Estados.SUCCES + " " + method + " Cantidad de tipos  líneas " + tipos.size());
		return ResponseEntity.ok().body(tipos);
	}

	@PutMapping(path = "/edit", consumes = { "application/json" })
	public ResponseEntity<Object> editarTipoLinea(@Valid @RequestBody TipoLinea cambio)
			throws DatoInvalidoException, ErrorInternoException, EntityNotFound {
		String method = "editarTipoLinea";
		logger.info(Estados.BEGIN + " " + method);
		if (cambio == null) {
			logger.info(Estados.ERROR + " " + method + " El objeto a editar no puede estar vacío");
			throw new DatoInvalidoException(" El objeto a editar no puede estar vacío");
		}
		if (cambio.getId() == null) {
			logger.info(Estados.ERROR + " " + method + " El id del objeto a editar no puede estar vacío");
			throw new DatoInvalidoException(" El id del objeto a editar no puede estar vacío");
		}
		TipoLinea tipo = repoTipoLinea.findById(cambio.getId()).orElse(null);
		if (tipo == null) {
			logger.info(Estados.ERROR + " " + method + "No existe un tipo línea con id " + cambio.getId());
			throw new EntityNotFound("No existe un tipo línea con id " + cambio.getId());
		}
		try {
			tipo.setDescripcion(cambio.getDescripcion());
			repoTipoLinea.save(tipo);
			logger.info(Estados.SUCCES + " " + method);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			logger.info(Estados.ERROR + " " + method + " Error inesperado " + e.getMessage());
			throw new ErrorInternoException(e.getMessage());
		}
	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<Object> bajaTipoLinea(@PathVariable(name = "id") Long idLinea)
			throws EntityNotFound, DatoInvalidoException, ErrorInternoException {
		String method = "bajaTipoLinea";
		logger.info(Estados.BEGIN + " " + method);
		if (idLinea == null) {
			logger.info(Estados.ERROR + " " + method);
			throw new DatoInvalidoException("El id del objeto no puede estar vacío");
		}
		TipoLinea tipo = repoTipoLinea.findById(idLinea).orElse(null);
		if (tipo == null) {
			logger.info(Estados.ERROR + " " + method + " No existe un tipo línea con id " + idLinea);
			throw new EntityNotFound("No existe un tipo línea con id " + idLinea);
		}
		try {
			repoTipoLinea.delete(tipo);
			logger.info(Estados.SUCCES + " " + method);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			logger.info(Estados.ERROR + " " + method + " " + e.getMessage());
			throw new ErrorInternoException(e.getMessage());
		}
	}

	@GetMapping(path = "/get/{id}", produces = { "application/json" })
	public ResponseEntity<Object> obtenerTipoLinea(@PathVariable(name = "id") Long idLinea)
			throws EntityNotFound, DatoInvalidoException, ErrorInternoException {
		String method = "obtenerTipoLinea";
		logger.info(Estados.BEGIN + " " + method);
		if (idLinea == null) {
			logger.info(Estados.ERROR + " " + method);
			throw new DatoInvalidoException("El id del objeto no puede estar vacío");
		}
		TipoLinea tipo = repoTipoLinea.findById(idLinea).orElse(null);
		if (tipo == null) {
			logger.info(Estados.ERROR + " " + method + " No existe un tipo línea con id " + idLinea);
			throw new EntityNotFound("No existe un tipo línea con id " + idLinea);
		}
		try {
			logger.info(Estados.SUCCES + " " + method);
			return ResponseEntity.ok().body(tipo);
		} catch (Exception e) {
			logger.info(Estados.ERROR + " " + method + " " + e.getMessage());
			throw new ErrorInternoException(e.getMessage());
		}
	}
}
