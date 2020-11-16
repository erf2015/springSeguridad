package uy.gub.imm.spring.utiles;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {
		Docket myConfig = new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("uy.gub.imm.spring.controller.rest"))
				.paths(PathSelectors.any()).build().apiInfo(getApiInfo());
		return myConfig;
	}

	private ApiInfo getApiInfo() {
		ApiInfo info = new ApiInfo("Title", "description", "Version 0.0.1",
				"http://localhost:8081/Seguridad/swagger-ui.html ", new Contact("fernando", "", "admin@gmail.com"),
				"license", "licenseUrl", Collections.emptyList());
		return info;
	}
}
