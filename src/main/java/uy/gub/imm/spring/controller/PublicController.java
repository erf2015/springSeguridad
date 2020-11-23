package uy.gub.imm.spring.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sun.istack.logging.Logger;

import uy.gub.imm.spring.dto.FiltroLineasDTO;
import uy.gub.imm.spring.dto.STMEntidadDTO;
import uy.gub.imm.spring.jpa.Institucion;
import uy.gub.imm.spring.jpa.Linea;
import uy.gub.imm.spring.jpa.Rol;
import uy.gub.imm.spring.jpa.StmEntidad;
import uy.gub.imm.spring.jpa.Subsistema;
import uy.gub.imm.spring.jpa.TipoLinea;
import uy.gub.imm.spring.jpa.Usuario;
import uy.gub.imm.spring.repositorios.InstitucionesRepositorio;
import uy.gub.imm.spring.repositorios.LineaRepositorio;
import uy.gub.imm.spring.repositorios.RolRepositorio;
import uy.gub.imm.spring.repositorios.StmEntidadRepositorio;
import uy.gub.imm.spring.repositorios.SubsistemaRepositorio;
import uy.gub.imm.spring.repositorios.TipoLineaRepositorio;
import uy.gub.imm.spring.repositorios.UsuarioRepositorio;
import uy.gub.imm.spring.utiles.Estados;
import uy.gub.imm.spring.utiles.GeneradorPass;

@Controller
public class PublicController {

	private static final Logger logger = Logger.getLogger(PublicController.class);

	@Autowired
	private StmEntidadRepositorio repoEntidad;

	@Autowired
	private UsuarioRepositorio repoUser;

	@Autowired
	private RolRepositorio repoRol;

	@Autowired
	private SubsistemaRepositorio repoSubs;

	@Autowired
	private TipoLineaRepositorio repoTipoLinea;

	@Autowired
	private LineaRepositorio repoLineas;

	@Autowired
	private InstitucionesRepositorio repoEmp;

	@Autowired
	private HttpServletRequest request;

	@Value("#{servletContext.contextPath}")
	private String servletContextPath;

	@PostConstruct
	public void init() {
		logger.info("Inicializar Start");
		String[] nombre = new String[] { "Primer elemento", "Segundo elemento", "Tercer elemento", "Cuarto elemento",
				"ABC", "QWE", "ERT", "ASD", "DDFV", "ERT34", "SFA", "WETWET", "NRY", "DFSDF", "SD SDG", "SDFHT",
				"TYJEY", "WEFBVSD", "VSFDFDVDSF", "SBWRT", "WERYFB", "VBDZFBSD", "ADFBA5T", "ADFBAE5", "AERGQ", "XVCB",
				"DFHE", "AFDYH", "NUTYSNS" };
		Long id = 1L;
		for (int i = 0; i < nombre.length; i++) {
			StmEntidad uno = new StmEntidad(id, nombre[i], new Date(), i + 1);
			repoEntidad.save(uno);
			id++;
		}
		// Usuario: USER/ADMIN Contraseña 1234
		Usuario admin = new Usuario();
		admin.setPassword("$2a$04$C7bQ5SCtEiGwiZLma7l2u.k/WFnTUaRyLfQcQIOuY4o30.QCKFeqK");// 1234
		admin.setConfirm("$2a$04$C7bQ5SCtEiGwiZLma7l2u.k/WFnTUaRyLfQcQIOuY4o30.QCKFeqK");
		admin.setUsername("admin@gmail.com");
		admin.setNombre("Fernando");
		admin.setApellido("AAAA");
		admin.setFechaAlta(new Date());
		admin.setDescripcion("Administrador del sistema");

		Rol adm = new Rol();
		adm.setAuthority("ADMIN");
		Rol us = new Rol();
		us.setAuthority("USER");

		adm = repoRol.save(adm);
		us = repoRol.save(us);

		Set<Rol> adminAutoritiess = new HashSet<>();
		adminAutoritiess.add(adm);
		adminAutoritiess.add(us);
		admin.setAuthority(adminAutoritiess);

		Usuario user = new Usuario();
		user.setPassword("$2a$04$C7bQ5SCtEiGwiZLma7l2u.k/WFnTUaRyLfQcQIOuY4o30.QCKFeqK");// 1234
		user.setConfirm("$2a$04$C7bQ5SCtEiGwiZLma7l2u.k/WFnTUaRyLfQcQIOuY4o30.QCKFeqK");
		user.setUsername("user@gmail.com");
		user.setNombre("Fernando");
		user.setApellido("AAAA");
		user.setFechaAlta(new Date());
		user.setDescripcion("Usuario del sistema con restricciones");

		Set<Rol> userAutoritiess = new HashSet<>();
		userAutoritiess.add(us);
		user.setAuthority(userAutoritiess);
		try {
			repoUser.save(admin);
			repoUser.save(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		logger.info("Inicialización correcta de la Base con datos y usuarios " + servletContextPath);
		cargaInicial();
	}

	private void cargaInicial() {
		logger.info("Inicialización cargaInicial subsistemas, tipos líneas");
		Subsistema uno = new Subsistema("Montevideo");
		Subsistema dos = new Subsistema("Canelones");
		Subsistema tres = new Subsistema("San José");
		Subsistema cuatro = new Subsistema("Rivera");
		Subsistema cinco = new Subsistema("Pando");
		repoSubs.save(uno);
		repoSubs.save(dos);
		repoSubs.save(tres);
		repoSubs.save(cuatro);
		repoSubs.save(cinco);
		logger.info("CargaInicial Terminanda subsistemas");
		TipoLinea dif = new TipoLinea("Diferencial");
		TipoLinea tur = new TipoLinea("Turístico");
		TipoLinea zon = new TipoLinea("Zonal");
		TipoLinea urb = new TipoLinea("Urbana");
		TipoLinea ite = new TipoLinea("Interurbana");
		repoTipoLinea.save(dif);
		repoTipoLinea.save(tur);
		repoTipoLinea.save(zon);
		repoTipoLinea.save(urb);
		repoTipoLinea.save(ite);
		logger.info("CargaInicial Terminanda tipos líneas");
		Institucion a = new Institucion("CITA");
		Institucion b = new Institucion("CO DEL ESTE");
		Institucion c = new Institucion("COETC");
		Institucion d = new Institucion("COMESA");
		Institucion e = new Institucion("CUTSA");
		Institucion f = new Institucion("CASANOVA");
		repoEmp.save(a);
		repoEmp.save(b);
		repoEmp.save(c);
		repoEmp.save(d);
		repoEmp.save(e);
		repoEmp.save(f);
		logger.info("CargaInicial terminanda instituciones");
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/login")
	public String autenticacion() {
		return "index";
	}

	@GetMapping("/register")
	public String register(Model model) {
		Usuario user = new Usuario();
		user.setHasError(false);
		model.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/register/add")
	public String registerAdd(@Valid Usuario user, BindingResult validacion, Model modelo) {
		String URL = "index";
		String method = "registerAdd";
		logger.info("BEGIN: " + method);
		Set<Rol> roles = new HashSet<>();
		boolean error = false;
		Map<String, String> msg = new HashMap<String, String>();
		if (validacion.hasErrors()) {
			logger.info("ERROR: " + method + " Errores de validación");
			List<ObjectError> errores = validacion.getAllErrors();
			for (ObjectError objectError : errores) {
				msg.put(objectError.getCodes()[1], objectError.getDefaultMessage());
				logger.info(objectError.getCodes()[1] + " -- " + objectError.getDefaultMessage());
			}
			modelo.addAttribute("message", msg);
			roles = new HashSet<>();
			roles.addAll(repoRol.findAll());
			modelo.addAttribute("roles", roles);
			user.setHasError(true);
			modelo.addAttribute("user", user);
			URL = "register";
			return URL;
		}
		logger.info(" " + method + " no tiene problemas con los campos");
		if (!user.getPassword().equals(user.getConfirm())) {
			logger.info("ERROR: " + method + " La contraseñas no coinciden");
			msg.put("confirm", "Las contraseñas no coinciden");
			modelo.addAttribute("message", msg);
			roles = new HashSet<>();
			roles.addAll(repoRol.findAll());
			modelo.addAttribute("roles", roles);
			user.setHasError(true);
			modelo.addAttribute("user", user);
			URL = "register";
			return URL;
		}
		logger.info(" " + method + " las password coniciden");
		Usuario repetido = repoUser.findByUsername(user.getUsername()).orElse(null);
		if (repetido == null) {
			logger.info(" " + method + " el usuario no existe");
			Rol rol = repoRol.findById(new Long(2)).orElse(null);
			if (rol != null) {
				logger.info(" " + method + " Todo ok vamos a ponerle el rol");
				Set<Rol> userAutoritiess = new HashSet<>();
				userAutoritiess.add(rol);
				user.setAuthority(userAutoritiess);
				user.setPassword(new GeneradorPass().main(user.getPassword()));
				user.setFechaAlta(new Date());
				repoUser.save(user);
			} else {
				System.out.println("Sucedio un error");
				msg.put("msg", "Sucedió un error con los roles");
				modelo.addAttribute("message", msg);
				modelo.addAttribute("user", user);
				error = true;
			}
		} else {
			logger.info("El email " + user.getUsername() + " ya existe.");
			msg.put("NotBlank.username", "El email " + user.getUsername() + " ya existe.");
			modelo.addAttribute("message", msg);
			modelo.addAttribute("user", user);
			URL = "register";
			return URL;
		}
		logger.info("Todo " + URL);
		if (!error) {
			msg.put("msg", "El usuario " + user.getUsername() + " se creó con éxito");
			modelo.addAttribute("message", msg);
		}
		return URL;
	}

	@GetMapping("/dist/index")
	public String boostrap(Model model) {
		List<STMEntidadDTO> entidades = new ArrayList<>();
		Iterable<StmEntidad> listado = repoEntidad.findAll();
		for (StmEntidad iterator : listado) {
			entidades.add(new STMEntidadDTO(iterator));
		}
		model.addAttribute("entidades", entidades);
		model.addAttribute("user", request.getUserPrincipal().getName());
		return "/dist/index";
	}

	@GetMapping("/consultaLineas")
	public String filtrarLineas(Model model) {
		FiltroLineasDTO filtro = new FiltroLineasDTO();
		List<Linea> lineas = repoLineas.obtenerLineasPorCriterios("-1", "-1", -1, "-1", "-1");// findAll();
		List<Linea> lineasAll = repoLineas.findAll();
		List<Subsistema> subsistemasAll = repoSubs.findAll();
		List<Institucion> empresasAll = repoEmp.findAll();
		List<TipoLinea> tiposLineasAll = repoTipoLinea.findAll();
		model.addAttribute("tiposLineasAll", tiposLineasAll);
		model.addAttribute("empresasAll", empresasAll);
		model.addAttribute("subsistemasAll", subsistemasAll);
		model.addAttribute("lineas", lineas);
		model.addAttribute("lineasAll", lineasAll);
		model.addAttribute("filtroLinea", filtro);
		FiltroLineasDTO nueva = new FiltroLineasDTO();
		model.addAttribute("nuevaLinea", nueva);
		try {
			URL url = new URL(request.getRequestURL().toString());
			String host = url.getHost();
			String userInfo = url.getUserInfo();
			String scheme = url.getProtocol();
			int port = url.getPort();
			String path = (String) request.getAttribute("javax.servlet.forward.request_uri");
			String query = (String) request.getAttribute("javax.servlet.forward.query_string");
			URI uri = new URI(scheme, userInfo, host, port, path, query, null);
			model.addAttribute("path", uri.toString() + servletContextPath);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/dist/gestionLineas";
	}

	@GetMapping("/consultaEntidades")
	public String consultaEntidades(Model model) {
		List<STMEntidadDTO> entidades = new ArrayList<>();
		Iterable<StmEntidad> listado = repoEntidad.findAll();
		for (StmEntidad iterator : listado) {
			entidades.add(new STMEntidadDTO(iterator));
		}
		model.addAttribute("entidades", entidades);
		return "/dist/gestionEntidades";
	}

	@PostMapping("/admin/editarLinea")
	public String editarDatosLinea(HttpServletRequest request) {
		Linea editar = new Linea();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		editar.setNombre(request.getParameter("editNombrePublico").trim());
		editar.setTarifaXKm(request.getParameter("editTrifaSelect").trim());
		Long idLinea = new Long(request.getParameter("editLineaID"));
		editar.setId(idLinea);
		String fecha = request.getParameter("editarFechaPWebDesde") != null
				? request.getParameter("editarFechaPWebDesde").trim()
				: null;
		if (fecha != null && !fecha.isEmpty()) {
			editar.setPublicableWebDesde(LocalDate.parse(fecha, formatter));
		}
		fecha = request.getParameter("editarFechaPWebHasta") != null
				? request.getParameter("editarFechaPWebHasta").trim()
				: null;
		if (fecha != null && !fecha.isEmpty()) {
			editar.setPublicableWebHasta(LocalDate.parse(fecha, formatter));
		}
		fecha = request.getParameter("editarFechaVigencia") != null ? request.getParameter("editarFechaVigencia").trim()
				: null;
		if (fecha != null && !fecha.isEmpty()) {
			editar.setFechaVigencia(LocalDate.parse(fecha, formatter));
		}
		fecha = request.getParameter("editarFechaBaja") != null ? request.getParameter("editarFechaBaja").trim() : null;
		if (fecha != null && !fecha.isEmpty()) {
			editar.setFechaBaja(LocalDate.parse(fecha, formatter));
		}
		Long subsistemaId = new Long(request.getParameter("editSubsistemaSelect").trim());
		Long codigoTipoLinea = new Long(request.getParameter("editTipoLineaSelect").trim());
		Subsistema sub = repoSubs.findById(subsistemaId).orElse(null);
		editar.setSubsistema(sub);
		TipoLinea tLinea = repoTipoLinea.findById(codigoTipoLinea).orElse(null);
		editar.setTipoLinea(tLinea);
		repoLineas.saveAndFlush(editar);
		return "redirect:/consultaLineas";
	}

	@PostMapping(path = "/admin/bajaLinea")
	public String bajaLinea(HttpServletRequest request) {
		String method = "bajaLinea";
		logger.info(Estados.BEGIN + " " + method);
		Long idLinea = new Long(request.getParameter("editLineaID"));
		Linea linea = repoLineas.findById(idLinea).orElse(null);
		if (linea != null) {
			String deleteFechaBaja = request.getParameter("deleteFechaBaja");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			linea.setFechaBaja(LocalDate.parse(deleteFechaBaja, formatter));
			repoLineas.save(linea);
			logger.info(Estados.SUCCES + " " + method);
		} else {
			logger.info(Estados.ERROR + " " + method + " no se encontró la línea con id " + idLinea);
		}
		return "redirect:/consultaLineas";
	}

	@PostMapping("/filtrar")
	public String filtrarLineas(HttpServletRequest request, FiltroLineasDTO filtro, Model model) {
		String method = "filtrarLineas";
		logger.info(Estados.BEGIN + " " + method);
		String nombre = filtro.getNombreLinea();// request.getParameter("Nombre").trim();
		String codigo = filtro.getCodigoLinea() != null ? filtro.getCodigoLinea().toString() : "-1";// request.getParameter("codigo").trim();
		Integer estado = filtro.getEstadoLinea() != null ? Integer.parseInt(filtro.getEstadoLinea()) : -1;// Integer.parseInt(request.getParameter("estado").trim());
		String subsistema = filtro.getSubsistemaSelected() != null ? filtro.getSubsistemaSelected().toString() : "-1";// request.getParameter("subsistemas").trim();
		String Empresa = filtro.getEmpresaSelected() != null ? filtro.getEmpresaSelected().toString() : "-1";// request.getParameter("empresa").trim();
		List<Linea> lineas = repoLineas.obtenerLineasPorCriterios(nombre, codigo, estado, subsistema, Empresa);
		List<Linea> lineasAll = repoLineas.findAll();
		if (lineas == null) {
			lineas = new ArrayList<>();
		}
		List<Subsistema> subsistemasAll = repoSubs.findAll();
		List<Institucion> empresasAll = repoEmp.findAll();
		List<TipoLinea> tiposLineasAll = repoTipoLinea.findAll();
		model.addAttribute("tiposLineasAll", tiposLineasAll);
		model.addAttribute("empresasAll", empresasAll);
		model.addAttribute("subsistemasAll", subsistemasAll);
		model.addAttribute("lineas", lineas);
		model.addAttribute("lineasAll", lineasAll);
		model.addAttribute("filtroLinea", filtro);
		FiltroLineasDTO nueva = new FiltroLineasDTO();
		model.addAttribute("nuevaLinea", nueva);
		try {
			URL url = new URL(request.getRequestURL().toString());
			String host = url.getHost();
			String userInfo = url.getUserInfo();
			String scheme = url.getProtocol();
			int port = url.getPort();
			String path = (String) request.getAttribute("javax.servlet.forward.request_uri");
			String query = (String) request.getAttribute("javax.servlet.forward.query_string");
			URI uri = new URI(scheme, userInfo, host, port, path, query, null);
			model.addAttribute("path", uri.toString() + servletContextPath);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/dist/gestionLineas";
	}
}
