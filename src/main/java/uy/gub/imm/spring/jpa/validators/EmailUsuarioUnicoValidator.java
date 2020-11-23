package uy.gub.imm.spring.jpa.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import uy.gub.imm.spring.jpa.validators.annotations.UsuarioUnicoAnotacion;
import uy.gub.imm.spring.servicios.CampoUnicoDelObjeto;

@Component
public class EmailUsuarioUnicoValidator implements ConstraintValidator<UsuarioUnicoAnotacion, String> {

	private String username;

	private String campo;

	// https://codingexplained.com/coding/java/hibernate/unique-field-validation-using-hibernate-spring
	@Autowired
	private ApplicationContext context;

	private CampoUnicoDelObjeto unicoInterface;

	@Override
	public void initialize(UsuarioUnicoAnotacion constraintAnnotation) {
		this.username = constraintAnnotation.username();
		Class<? extends CampoUnicoDelObjeto> clazz = constraintAnnotation.service();
		this.campo = constraintAnnotation.fieldName();
		String serviceQualifier = constraintAnnotation.serviceQualifier();
		if (!serviceQualifier.equals("")) {
			this.unicoInterface = this.context.getBean(serviceQualifier, clazz);
		} else {
			this.unicoInterface = this.context.getBean(clazz);
		}
	}

	// https://stackoverflow.com/questions/41820182/unknown-property-error-while-using-beanutils-getproperty
	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		Object segunda = null;
		try {
			segunda = PropertyUtils.getProperty(arg0, this.username);
		} catch (Exception e) {
			System.out.println(EmailUsuarioUnicoValidator.class.getName() + " - " + e.getMessage());
		}
		if (segunda == null) {
			segunda = arg0.toString();
		}
		if (unicoInterface == null) {
			System.out.println("El repo esta null");
		}
		try {
			System.out.println("El usuario con username: " + arg0 + " " + segunda + " " + this.campo);
			if (segunda != null) {
				boolean existe = unicoInterface.existeValorEnEntidad(arg0, this.campo);
				System.out.println("pasa la b'usqueda en el repo");
				if (!existe) {
					System.out.println(
							"El usuario con username " + this.username.toString() + " no existe en el sistema");
					return true;
				} else {
					System.out.println(
							"El usuario con username " + this.username.toString() + " ya existe en el sistema");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(EmailUsuarioUnicoValidator.class.getName() + " - " + e.getMessage());
		}
		return false;
	}

}