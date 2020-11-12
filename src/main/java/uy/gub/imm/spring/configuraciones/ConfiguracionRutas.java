package uy.gub.imm.spring.configuraciones;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionRutas implements WebMvcConfigurer {

	/**
	 * Vamos a mapear aquí los alias de las paginas con que contara la app para que
	 * se direccione hacia ellas según lo descrito aquí. Las páginas están ubicadas
	 * en la carpeta src/main/resources/template/paginas.....html
	 */
	public void addViewControllers(ViewControllerRegistry vistas) {
		vistas.addViewController("/home").setViewName("home");
		vistas.addViewController("/").setViewName("home");
		vistas.addViewController("/hello").setViewName("hello");
		vistas.addViewController("/login").setViewName("login");
		/*
		 * vistas.addViewController("/login").setViewName("login");
		 * vistas.addViewController("/home").setViewName("home");
		 * vistas.addViewController("/administracion").setViewName("admin");
		 * vistas.addViewController("/entidades").setViewName("entidades");
		 * vistas.addViewController("/entidad").setViewName("entidad");
		 * vistas.addViewController("/formulario").setViewName("formulario");
		 */
	}
}
