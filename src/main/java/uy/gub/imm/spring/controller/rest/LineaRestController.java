package uy.gub.imm.spring.controller.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.gub.imm.spring.dto.MensajeStatus;
import uy.gub.imm.spring.jpa.Linea;
import uy.gub.imm.spring.repositorios.LineaRepositorio;
import uy.gub.imm.spring.repositorios.SubsistemaRepositorio;
import uy.gub.imm.spring.repositorios.TipoLineaRepositorio;
import uy.gub.imm.spring.utiles.Estados;

@RestController
@RequestMapping(path = "/servicio/linea")
public class LineaRestController {

	private Logger logger = LoggerFactory.getLogger(LineaRestController.class);

	@Autowired
	private LineaRepositorio repoLinea;

	@Autowired
	private TipoLineaRepositorio repoTipoLinea;

	@Autowired
	private SubsistemaRepositorio repoSubsistema;

	@GetMapping(path = "/add", produces = { "application/json" })
	public ResponseEntity<MensajeStatus> adicionarLinea(@PathParam("nombre") String nombre,
			@PathParam("codigoSub") Long codigoSub, @PathParam("tipoLinea") Long tipoLinea,
			@PathParam("fecha") String fecha) {
		String method = "adicionarLinea";
		logger.info(Estados.BEGIN + " " + method + " parámetros {nombre} " + nombre + " {subistema} " + codigoSub
				+ " {tipoLinea} " + tipoLinea + " {fecha} " + fecha);
		MensajeStatus msg = null;
		try {
			List<Linea> lineas = repoLinea.obtenerLineasPorNombre(nombre);
			if (lineas != null) {
				msg = new MensajeStatus("Ya existe una línea con ese nombre.", 409);
				logger.info(Estados.ERROR + " " + method + " Ya existe una línea con ese nombre.");
				return new ResponseEntity<MensajeStatus>(msg, HttpStatus.OK);
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
			msg = new MensajeStatus(" Error inesperado " + e.getMessage(), 500);
			return new ResponseEntity<MensajeStatus>(msg, HttpStatus.OK); 
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

}
