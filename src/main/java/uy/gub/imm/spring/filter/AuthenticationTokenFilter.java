package uy.gub.imm.spring.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import uy.gub.imm.spring.servicios.ServicioUsuarioImpl;
import uy.gub.imm.spring.utiles.JwtUtils;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwt;

	@Autowired
	private ServicioUsuarioImpl userDetailService;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = extraerToken(request);
			if (token != null && jwt.authenticarToken(token)) {
				String nombre = jwt.obtenerNombre(token);
				UserDetails userdetails = userDetailService.loadUserByUsername(nombre);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userdetails.getUsername(), null, userdetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				logger.info("Vigencia hasta " + jwt.obtenerFechaExpiracion(token));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		} catch (Exception e) {
			logger.info("AuthenticationTokenFilter:doFilterInternal " + e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

	private String extraerToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer")) {
			logger.info("extraerToken: Trae token");
			String response = header.substring(7, header.length());
			return response;
		} else {
			logger.info("extraerToken: No trae token");
			return null;
		}
	}

}
