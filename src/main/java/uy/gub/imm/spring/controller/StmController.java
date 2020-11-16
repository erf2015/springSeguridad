package uy.gub.imm.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sun.istack.logging.Logger;

import uy.gub.imm.spring.dto.STMEntidadDTO;
import uy.gub.imm.spring.jpa.StmEntidad;
import uy.gub.imm.spring.repositorios.StmEntidadRepositorio;

@Controller
public class StmController {

	private static final Logger logger = Logger.getLogger(StmController.class);

	@Autowired
	private StmEntidadRepositorio servicio;

	/**
	 * Te redirecciona a la pagina a suministrar los datos de la nueva entidad.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/admin/entidad/alta")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public String formularioAltaEntidad(Model model) {
		System.out.println("Accede con su rol formularioAltaEntidad");
		model.addAttribute("entidad", new STMEntidadDTO());
		return "/admin/formulario";
	}

	/**
	 * Incorpora una nueva entidad en base. Redirecciona a la funcion obtenerEntidad
	 * suministrando el id de la misma
	 * 
	 * @param entidad
	 * @param model
	 * @return
	 */
	@PostMapping("/admin/entidad")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public String crearEntidadSTM(STMEntidadDTO entidad, Model model) {
		System.out.println("-> " + entidad.toString());
		StmEntidad entity = new StmEntidad();
		entidad.setId(servicio.count() + 1);
		entity.setId(entidad.getId());
		entity.setDescripcion(entidad.getDescripcion());
		entity.setFechaBaja(entidad.getFechaBaja());
		entity.setSubsistema(entidad.getSubsistema());
		servicio.save(entity);
		return "redirect:/privado/entidades";
	}

	/**
	 * Mostrar la entidad seleccionada por Id.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/admin/entidad/{id}")
	// @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
	public String obtenerEntidad(@PathVariable Long id, Model model) {
		STMEntidadDTO entidad = null;
		StmEntidad entity = servicio.findById(id).orElse(new StmEntidad());
		if (entity.getId() != null) {
			entidad = new STMEntidadDTO(entity);
		}
		model.addAttribute("entidad", entidad);
		return "/admin/entidad";
	}

	/**
	 * Obtener todas las entidades almacenadas.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/privado/entidades")
	public String mostrarListadoEntidades(Model model) {
		List<STMEntidadDTO> entidades = new ArrayList<>();
		Iterable<StmEntidad> listado = servicio.findAll();
		for (StmEntidad iterator : listado) {
			entidades.add(new STMEntidadDTO(iterator));
		}
		model.addAttribute("entidades", entidades);
		return "/privado/entidades";
	}


	/**
	 * Buscar la entidad según el id provisto. Redirecciona a la vista según el id
	 * provisto
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/admin/entidad/editar/{id}")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public String enviarEntidadEditar(@PathVariable Long id, Model model) {
		System.out.println("Accede con su rol enviarEntidadEditar");
		StmEntidad entity = servicio.findById(id).orElse(new StmEntidad());
		STMEntidadDTO modificar = new STMEntidadDTO(entity);
		model.addAttribute("entidad", modificar);
		return "/admin/formulario";
	}

	/**
	 * Guarda la entidad en base.
	 * 
	 * @param entidad
	 * @return
	 */
	@PostMapping("/admin/entidad/{id}")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public String actualizarEntidad(STMEntidadDTO entidad) {
		StmEntidad entity = new StmEntidad();
		entity.setId(entidad.getId());
		entity.setDescripcion(entidad.getDescripcion());
		entity.setFechaBaja(entidad.getFechaBaja());
		entity.setSubsistema(entidad.getSubsistema());
		servicio.save(entity);
		return "redirect:/admin/entidad/" + entidad.getId();
	}

	@GetMapping("/admin/entidad/eliminar/{id}")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public String bajaEntidad(@PathVariable Long id) {
		System.out.println("Accede con su rol bajaEntidad");
		servicio.deleteById(id);
		return "redirect:/privado/entidades";
	}

}
