package uy.gub.imm.spring.controller.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.gub.imm.spring.jpa.StmEntidad;
import uy.gub.imm.spring.repositorios.StmEntidadRepositorio;
import uy.gub.imm.spring.servicios.ServicioReporteEntidades;

@RestController
@RequestMapping(path = "/servicio")
public class StmRestController {

	private static final Logger logger = LoggerFactory.getLogger(StmRestController.class);

	@Autowired
	private StmEntidadRepositorio servicio;

	@Autowired
	private ServicioReporteEntidades reporte;

	@GetMapping(path = "/get/{id}")
	public ResponseEntity<StmEntidad> obtenerEntidadPorID(@PathVariable(name = "id", required = true) Long id)
			throws EntityNotFoundException {
		String metodo = "obtenerEntidadPorID";
		logger.debug(metodo + " Parámetro ID: ", id);
		StmEntidad ent = servicio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("La entidad con código " + id + " no existe en base."));
		return ResponseEntity.ok().body(ent);
	}

	@GetMapping(path = "/")
	public ResponseEntity<Object> entidades() {
		Iterable<StmEntidad> resultado = servicio.findAll();
		return ResponseEntity.ok().body(resultado);
	}

	@PostMapping(path = "/post/")
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

	/**
	 * Crea el reporte y lo descarga a una ubicación fija
	 * 
	 * @param formato
	 * @return
	 */
	@GetMapping(path = "/report/{formato}")
	public ResponseEntity<Object> reporte(@PathVariable(value = "formato") String formato) {
		String responseEntity = reporte.exportarEntidadesLocal(formato);
		return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
	}

	/**
	 * Crea el reporte y lo descarga
	 * 
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(path = "/report/download/{formato}")
	public void jasperReport(@PathVariable("formato") String formato, HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		response.setContentType("application/x-download");
		if (formato.equalsIgnoreCase("pdf")) {
			response.addHeader("Content-Disposition", "attachment; filename=entidadesReporte.pdf;");
			logger.info("nuevoReport INFO Generado PDF");
			reporte.descargarReporte(formato, out);
		} else if (formato.equalsIgnoreCase("xls")) {
			response.addHeader("Content-Disposition", "attachment; filename=entidadesReporte.xls;");
			logger.info("nuevoReport INFO Generado XLS");
			reporte.descargarReporte("xls", out);
		} else if (formato.equalsIgnoreCase("doc")) {
			response.addHeader("Content-Disposition", "attachment; filename=entidadesReporte.docx;");
			logger.info("nuevoReport INFO Generado DOC");
			reporte.descargarReporte("doc", out);
		} else if (formato.equalsIgnoreCase("csv")) {
			response.addHeader("Content-Disposition", "attachment; filename=entidadesReporte.csv;");
			logger.info("nuevoReport INFO Generado CSV");
			reporte.descargarReporte("csv", out);
		} else {// html

		}
	}
}
