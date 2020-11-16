package uy.gub.imm.spring.test.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashMap;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import uy.gub.imm.spring.utiles.ApiResponseDTO;
import uy.gub.imm.spring.utiles.JWTRequestToken;
import uy.gub.imm.spring.utiles.JWTResponseToken;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JWTRestControllerTest {

	@Autowired
	private MockMvc myMVC;

	@Autowired
	private ObjectMapper mapper;

	private String token;

	private JWTResponseToken responseToken;

	private static final Logger logger = LoggerFactory.getLogger(JWTRestControllerTest.class);

	@Test
	@Order(value = 0)
	public void testHome() {
		logger.info("testHome");
		MvcResult result;
		try {
			result = myMVC.perform(get("/jwt/home")).andReturn();
			token = result.getResponse().getContentAsString();
			System.out.println("Contenido del body " + token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(value = 1)
	public void testNuevoUser() {
		logger.info("testNuevoUser");
		JWTRequestToken request = new JWTRequestToken("test@gmail.com", "test", "test", "1234");
		if (myMVC != null) {
			try {
				MvcResult result = myMVC.perform(
						post("/jwt/add").contentType("application/json").content(mapper.writeValueAsString(request)))
						.andExpect(status().isOk()).andReturn();
				token = result.getResponse().getContentAsString();
				System.out.println("Contenido del body " + token);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("El object mvc esta null;");
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	@Order(value = 2)
	public void testValidarToken() {
		logger.info("testValidarToken");
		JWTRequestToken request = new JWTRequestToken("test@gmail.com", "test", "test", "1234");
		if (myMVC != null) {
			try {
				MvcResult result = myMVC.perform(post("/jwt/authenticar").contentType("application/json")
						.content(mapper.writeValueAsString(request))).andReturn();
				String body = result.getResponse().getContentAsString();
				responseToken = mapper.readValue(body, JWTResponseToken.class);
				int status = result.getResponse().getStatus();
				System.out.println("El status es " + status);
				System.out.println(responseToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("testAllUser");
			try {
				MvcResult result = myMVC
						.perform(get("/servicio/").header("Authorization", "Bearer " + responseToken.getToken()))
						.andReturn();
				String response = result.getResponse().getContentAsString();
				ApiResponseDTO dto = mapper.readValue(response, ApiResponseDTO.class);
				System.out.println(dto.getDatos());
				LinkedHashMap<Object, Object> entidades = (LinkedHashMap<Object, Object>) dto.getDatos();
				System.out.println(entidades.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("El object mvc esta null;");
		}
	}

}
