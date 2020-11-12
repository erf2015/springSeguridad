package uy.gub.imm.spring.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sun.istack.logging.Logger;

import uy.gub.imm.spring.jpa.Rol;
import uy.gub.imm.spring.jpa.Usuario;
import uy.gub.imm.spring.repositorios.RolRepositorio;
import uy.gub.imm.spring.repositorios.UsuarioRepositorio;
import uy.gub.imm.spring.utiles.GeneradorPass;

@Controller
@PreAuthorize(value = "hasAuthority('ADMIN')")
public class AdminController {

	private static final Logger logger = Logger.getLogger(AdminController.class);

	private Set<Rol> roles;
	private List<Usuario> usuarios;

	@Autowired
	private RolRepositorio rolCrud;

	@Autowired
	private UsuarioRepositorio usuerCrud;

	@PostConstruct
	public void init() {
		roles = new HashSet<>();
	}

	@GetMapping("/password")
	public String password(Model model) {
		Usuario user = new Usuario();
		user.setHasError(false);
		model.addAttribute("user", user);
		return "password";
	}

	/**
	 * Ir a la vista de creacion de nuevo rol
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(path = "/admin/rol")
	public String creacionRoles(Model model) {
		Rol rol = new Rol();
		model.addAttribute("rol", rol);
		return "/admin/rol";
	}

	/**
	 * EndPoint para adicionar un nuevo rol
	 * 
	 * @param rol
	 * @return
	 */
	@PostMapping(path = "/admin/add/rol")
	public String addRol(Rol rol) {
		rolCrud.save(rol);
		return "redirect:/privado/entidades";
	}

	/**
	 * Tabla con todos los usuarios
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(path = "/admin/usuario/listado")
	public String listadoUsarios(Model model) {
		usuarios = usuerCrud.findAll();
		model.addAttribute("usuarios", usuarios);
		return "/admin/usuarios";
	}

	/**
	 * Ir a la vista de nuevo usuario
	 * 
	 * @param model
	 * @return
	 */

	@GetMapping(path = "/admin/usuario")
	public String creacionUsarios(Model model) {
		Usuario user = new Usuario();
		user.setHasError(false);
		model.addAttribute("user", user);
		roles = new HashSet<>();
		roles.addAll(rolCrud.findAll());
		model.addAttribute("roles", roles);
		return "/admin/usuario";
	}

	/**
	 * EndPoint para adicionar usuario ademas de que este viene con un rol asignado
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(path = "/admin/usuario/add")
	public String addUsuario(@Valid Usuario user, BindingResult validacion, Model modelo) {
		String URL = "/admin/usuario";
		Map<String, String> msg = new HashMap<String, String>();
		if (validacion.hasErrors()) {
			List<ObjectError> errores = validacion.getAllErrors();
			for (ObjectError objectError : errores) {
				msg.put(objectError.getCodes()[1], objectError.getDefaultMessage());
			}
			modelo.addAttribute("message", msg);
			roles = new HashSet<>();
			roles.addAll(rolCrud.findAll());
			modelo.addAttribute("roles", roles);
			user.setHasError(true);
			modelo.addAttribute("user", user);
			return URL;
		}
		if (!user.getPassword().equals(user.getConfirm())) {
			msg.put("confirm", "Las contraseñas no coinciden");
			modelo.addAttribute("message", msg);
			roles = new HashSet<>();
			roles.addAll(rolCrud.findAll());
			modelo.addAttribute("roles", roles);
			user.setHasError(true);
			modelo.addAttribute("user", user);
			return URL;
		}
		Rol rol = rolCrud.findById(user.getIdRol()).orElse(null);
		if (rol != null) {
			Set<Rol> userAutoritiess = new HashSet<>();
			userAutoritiess.add(rol);
			user.setAuthority(userAutoritiess);
			user.setPassword(new GeneradorPass().main(user.getPassword()));
			user.setFechaAlta(new Date());
			usuerCrud.save(user);
			URL = "redirect:/admin/usuario/listado";
		} else {
			System.out.println("Sucedio un error");
			URL += "?error";
		}
		return URL;
	}

	/**
	 * 
	 * EndPoint para dar de baja un usuario por id
	 * 
	 * @param idUsuario
	 * @return
	 */
	@GetMapping(path = "/admin/usuario/remove/{id}")
	public String removeUsuario(@PathVariable(name = "id") Long idUsuario) {
		Usuario user = usuerCrud.findById(idUsuario).orElse(null);
		if (user != null) {
			usuerCrud.deleteById(idUsuario);
			return "redirect:/admin/usuario/listado";
		} else {
			System.out.println("El usuario no existe en la base");
			return "redirect:/privado/entidades";
		}
	}

}
