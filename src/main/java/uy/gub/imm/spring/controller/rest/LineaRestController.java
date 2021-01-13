package uy.gub.imm.spring.controller.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import uy.gub.imm.spring.dto.MensajeStatus;
import uy.gub.imm.spring.excepciones.DatoInvalidoException;
import uy.gub.imm.spring.excepciones.EntityNotFound;
import uy.gub.imm.spring.excepciones.ErrorInternoException;
import uy.gub.imm.spring.jpa.Linea;
import uy.gub.imm.spring.repositorios.LineaRepositorio;
import uy.gub.imm.spring.repositorios.SubsistemaRepositorio;
import uy.gub.imm.spring.repositorios.TipoLineaRepositorio;
import uy.gub.imm.spring.utiles.Estados;

@RestController
@RequestMapping(path = "/servicio/linea", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@CrossOrigin(origins = "*")
/*
 * , methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
 * RequestMethod.DELETE }
 */
public class LineaRestController {

	private Logger logger = LoggerFactory.getLogger(LineaRestController.class);

	@Autowired
	private LineaRepositorio repoLinea;

	@Autowired
	private TipoLineaRepositorio repoTipoLinea;

	@Autowired
	private SubsistemaRepositorio repoSubsistema;

	@PostMapping(path = "/add", produces = { "application/json" })
	public ResponseEntity<MensajeStatus> adicionarLinea(@PathParam("nombre") String nombre,
			@PathParam("codigoSub") Long codigoSub, @PathParam("tipoLinea") Long tipoLinea,
			@PathParam("fecha") String fecha) throws ErrorInternoException, DatoInvalidoException {
		String method = "adicionarLinea";
		logger.info(Estados.BEGIN + " " + method + " parámetros {nombre} " + nombre + " {subistema} " + codigoSub
				+ " {tipoLinea} " + tipoLinea + " {fecha} " + fecha);
		MensajeStatus msg = null;
		try {
			List<Linea> lineas = repoLinea.obtenerLineasPorNombre(nombre);
			if (lineas != null) {
				msg = new MensajeStatus("Ya existe una línea con ese nombre.", 409);
				logger.info(Estados.ERROR + " " + method + " Ya existe una línea con ese nombre.");
				// return new ResponseEntity<MensajeStatus>(msg, HttpStatus.OK);
				throw new DatoInvalidoException(" Ya existe una línea con ese nombre.");
			} else {
				Linea nueva = new Linea();
				nueva.setNombre(nombre);
				nueva.setSubsistema(repoSubsistema.findById(codigoSub).orElse(null));
				nueva.setTipoLinea(repoTipoLinea.findById(tipoLinea).orElse(null));
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate vigencia = LocalDate.parse(fecha, format);
				nueva.setFechaVigencia(vigencia);
				nueva.setPublicableWebDesde(vigencia);
				nueva.setTarifaXKm("S");
				repoLinea.save(nueva);
				logger.info(Estados.SUCCES + " " + method + " Se creó la línea con nombre " + nombre);
				msg = new MensajeStatus("OK", 200);
				return new ResponseEntity<MensajeStatus>(msg, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info(Estados.ERROR + " " + method + " Error inesperado " + e.getMessage());
			// msg = new MensajeStatus(" Error inesperado " + e.getMessage(), 500);
			// return new ResponseEntity<MensajeStatus>(msg, HttpStatus.OK);
			throw new ErrorInternoException(e.getMessage());
		}
	}

	@GetMapping(path = "/all", produces = { "application/json" })
	public ResponseEntity<Object> listarLineas() {
		String method = "listarLineas";
		logger.info(Estados.BEGIN + " " + method);
		List<Linea> lineas = repoLinea.findAll();
		logger.info(Estados.SUCCES + " " + method + " Cantidad de líneas " + lineas.size());
		return ResponseEntity.ok().body(lineas);
	}

	@PutMapping(path = "/edit", consumes = "application/json")
	public ResponseEntity<Object> editarLinea(@Valid @RequestBody Linea cambio)
			throws DatoInvalidoException, ErrorInternoException, EntityNotFound {
		String method = "editarLineas";
		logger.info(Estados.BEGIN + " " + method);
		if (cambio == null) {
			logger.info(Estados.ERROR + " " + method + " El objeto a editar no puede estar vacío");
			throw new DatoInvalidoException(" El objeto a editar no puede estar vacío");
		}
		if (cambio.getId() == null) {
			logger.info(Estados.ERROR + " " + method + " El id del objeto a editar no puede estar vacío");
			throw new DatoInvalidoException(" El id del objeto a editar no puede estar vacío");
		}
		Linea linea = repoLinea.findById(cambio.getId()).orElse(null);
		if (linea == null) {
			logger.info(Estados.ERROR + " " + method + "No existe una línea con id " + cambio.getId());
			throw new EntityNotFound("No existe una línea con id " + cambio.getId());
		}
		try {
			linea.setEmpresa(cambio.getEmpresa());
			linea.setFechaVigencia(cambio.getFechaVigencia());
			linea.setNombre(cambio.getNombre());
			linea.setPublicableWebDesde(cambio.getPublicableWebDesde());
			linea.setPublicableWebHasta(cambio.getPublicableWebHasta());
			linea.setSubsistema(cambio.getSubsistema());
			linea.setSublineas(cambio.getSublineas());
			linea.setTarifaXKm(cambio.getTarifaXKm());
			linea.setTipoLinea(cambio.getTipoLinea());
			repoLinea.save(linea);
			logger.info(Estados.SUCCES + " " + method);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			logger.info(Estados.ERROR + " " + method + " Error inesperado " + e.getMessage());
			throw new ErrorInternoException(e.getMessage());
		}
	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<Object> bajaLinea(@PathVariable(name = "id") Long idLinea)
			throws EntityNotFound, DatoInvalidoException, ErrorInternoException {
		String method = "bajaLinea";
		logger.info(Estados.BEGIN + " " + method);
		if (idLinea == null) {
			logger.info(Estados.ERROR + " " + method);
			throw new DatoInvalidoException("El id del objeto no puede estar vacío");
		}
		Linea linea = repoLinea.findById(idLinea).orElse(null);
		if (linea == null) {
			logger.info(Estados.ERROR + " " + method + " No existe una línea con id " + idLinea);
			throw new EntityNotFound("No existe una línea con id " + idLinea);
		}
		try {
			repoLinea.delete(linea);
			logger.info(Estados.SUCCES + " " + method);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			logger.info(Estados.ERROR + " " + method + " " + e.getMessage());
			throw new ErrorInternoException(e.getMessage());
		}
	}

	@GetMapping(path = "/get/{id}", produces = { "application/json" })
	public ResponseEntity<Object> obtenerLinea(@PathVariable(name = "id") Long idLinea)
			throws EntityNotFound, DatoInvalidoException, ErrorInternoException {
		String method = "obtenerLinea";
		logger.info(Estados.BEGIN + " " + method);
		if (idLinea == null) {
			logger.info(Estados.ERROR + " " + method);
			throw new DatoInvalidoException("El id del objeto no puede estar vacío");
		}
		Linea linea = repoLinea.findById(idLinea).orElse(null);
		if (linea == null) {
			logger.info(Estados.ERROR + " " + method + " No existe una línea con id " + idLinea);
			throw new EntityNotFound("No existe una línea con id " + idLinea);
		}
		try {
			logger.info(Estados.SUCCES + " " + method);
			return ResponseEntity.ok().body(linea);
		} catch (Exception e) {
			logger.info(Estados.ERROR + " " + method + " " + e.getMessage());
			throw new ErrorInternoException(e.getMessage());
		}
	}

}
