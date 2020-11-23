package uy.gub.imm.spring.jpa.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import uy.gub.imm.spring.jpa.validators.annotations.ComparaContrasennaAnotacion;

public class PasswordValidator implements ConstraintValidator<ComparaContrasennaAnotacion, Object> {

	private String contrasenna;

	private String confirmacion;
	/**
	 * Este m'etodo es muy relevante se debe conservar el nombre de initialize
	 */
	public void initialize(final ComparaContrasennaAnotacion anotacionValidator) {
		this.confirmacion = anotacionValidator.password();
		this.contrasenna = anotacionValidator.confirmacion();
	}

	@Override
	public boolean isValid(Object arg0, ConstraintValidatorContext arg1) {
		try {
			final Object primera = PropertyUtils.getProperty(arg0, this.contrasenna);
			final Object segunda = PropertyUtils.getProperty(arg0, this.confirmacion);
			return primera == null && segunda == null || primera != null && segunda != null && primera.equals(segunda);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

}
