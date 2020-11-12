package uy.gub.imm.spring.repositorios.custom;

import java.util.List;

import uy.gub.imm.spring.jpa.Linea;

public interface LineaRepositorioCustom {

	public List<Linea> obtenerLineasPorNombre(String nombre);

	public List<Linea> obtenerLineasPorCriterios(String nombre, String codigo, int estado, String subsistema, String Empresa);
}
