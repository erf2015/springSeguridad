package uy.gub.imm.spring.utiles;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwt.key}")
	private String key;

	public String obtenerNombre(String token) {
		return obtenerClaims(token, Claims::getSubject);
	}

	public <T> T obtenerClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = obtenerTodosClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generarToken(UserDetails userdetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("roles", userdetails.getAuthorities());
		return crearToken(claims, userdetails);
	}

	public boolean validarToken(String token, UserDetails userDetails) {
		final String username = obtenerNombre(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpirado(token));
	}

	public Date obtenerFechaExpiracion(String token) {
		return obtenerClaims(token, Claims::getExpiration);
	}

	public boolean authenticarToken(String token) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	private Claims obtenerTodosClaims(String token) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpirado(String token) {
		return obtenerFechaExpiracion(token).before(new Date());
	}

	private String crearToken(Map<String, Object> claims, UserDetails userdetails) {
		return Jwts.builder().setClaims(claims).setSubject(userdetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(SignatureAlgorithm.HS512, key).compact();
	}

}
