package uy.gub.imm.spring.jpa.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import uy.gub.imm.spring.jpa.validators.FechaValidator;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FechaValidator.class)
@Documented
public @interface CompararFechasAnotacion {

	String message() default "La fecha uno debe anterior a la fecha dos.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String fechaUno();

	String fechaDos();

	@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		CompararFechasAnotacion[] fechas();
	}

}
