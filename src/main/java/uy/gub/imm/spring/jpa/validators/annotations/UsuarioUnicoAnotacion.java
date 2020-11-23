package uy.gub.imm.spring.jpa.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import uy.gub.imm.spring.jpa.validators.EmailUsuarioUnicoValidator;
import uy.gub.imm.spring.servicios.CampoUnicoDelObjeto;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailUsuarioUnicoValidator.class)
public @interface UsuarioUnicoAnotacion {

	String message() default "El usuario ya existe anotation.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends CampoUnicoDelObjeto> service();

	String serviceQualifier() default "";

	String fieldName();

	String username();

}
