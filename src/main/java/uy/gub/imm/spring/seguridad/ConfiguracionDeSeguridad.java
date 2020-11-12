package uy.gub.imm.spring.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uy.gub.imm.spring.servicios.ServicioUsuarioImpl;

/**
 * Aquí vamos a configurar quién puede acceder a que.
 * 
 * @author fernando
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracionDeSeguridad extends WebSecurityConfigurerAdapter {

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ServicioUsuarioImpl userDetailsService;
	// Necesario para evitar que la seguridad se aplique a los resources
	// Como los css, imagenes y javascripts
	String[] resources = new String[] { "/include/**", "/scripts/**", "/src/**", "/css/**", "/icons/**", "/img/**",
			"/js/**", "/layer/**", "/assets/**" };

	// Registra el service para usuarios y el encriptador de contrasena
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Setting Service to find User in the database.
		// And Setting PassswordEncoder
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	/**
	 * Aquí se establece a que url se le debe proporcionar seguridad y a cual no.
	 * 
	 * .defaultSuccessUrl("/privado/entidades").
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(resources).permitAll()
				.antMatchers("/", "/index", "/home", "/register**", "/register/add").permitAll().antMatchers("/admin*")
				.access("hasRole('ADMIN')").antMatchers("/privado*").access("hasRole('USER')").anyRequest()
				.authenticated().and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/dist/index")
				.failureUrl("/login?error=true").usernameParameter("username").passwordParameter("password").and()
				.logout().permitAll().logoutSuccessUrl("/login?logout");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
//El numero 4 representa que tan fuerte quieres la encriptacion.
//Se puede en un rango entre 4 y 31. 
//Si no pones un numero el programa utilizara uno aleatoriamente cada vez
//que inicies la aplicacion, por lo cual tus contrasenas encriptadas no funcionaran bien
		return bCryptPasswordEncoder;
	}

}
