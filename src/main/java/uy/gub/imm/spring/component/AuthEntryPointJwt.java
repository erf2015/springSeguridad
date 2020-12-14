package uy.gub.imm.spring.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPoint.class);
	 
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;
	  
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.info("No esta autorizado no tiene una session válida.");
		// response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "La petición no está
		// autorizada ");   
		resolver.resolveException(request, response, null, authException);
		//response.setContentType("application/json");
		//response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		//response.getOutputStream().println("{ \"AuthenticationException error\": \"" + authException.getMessage() + "\" }");
	}
}
