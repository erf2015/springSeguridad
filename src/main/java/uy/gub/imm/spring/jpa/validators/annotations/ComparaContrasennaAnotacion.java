package uy.gub.imm.spring.jpa.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import uy.gub.imm.spring.jpa.validators.PasswordValidator;

//https://www.javadevjournal.com/spring-mvc/spring-mvc-custom-validator/

@Constraint(validatedBy = PasswordValidator.class)
@Documented
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ComparaContrasennaAnotacion {

	String message() default "Las contrasennas no coinciden Anotaci'on controller.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String password();

	String confirmacion();

	@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		ComparaContrasennaAnotacion[] valores();
	}

}
