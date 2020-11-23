package uy.gub.imm.spring.jpa.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import uy.gub.imm.spring.jpa.validators.annotations.CompararFechasAnotacion;

public class FechaValidator implements ConstraintValidator<CompararFechasAnotacion, Object> {

	private String fechaUno;

	private String fechaDos;

	@Override
	public void initialize(CompararFechasAnotacion constraintAnnotation) {
		this.fechaUno = constraintAnnotation.fechaUno();
		this.fechaDos = constraintAnnotation.fechaDos();
	}

	@Override
	public boolean isValid(Object arg0, ConstraintValidatorContext arg1) {
		try {
			final Object primero = PropertyUtils.getProperty(arg0, this.fechaUno);
			final Object segundo = PropertyUtils.getProperty(arg0, this.fechaDos);
			if (primero != null && segundo != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate desde = LocalDate.parse(fechaUno, formatter);
				LocalDate hasta = LocalDate.parse(fechaDos, formatter);
				if (hasta.isAfter(desde)) {
					return false;
				}
				return true;
			} else if (segundo != null && primero == null) {
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
