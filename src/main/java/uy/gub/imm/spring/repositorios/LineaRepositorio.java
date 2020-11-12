package uy.gub.imm.spring.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import uy.gub.imm.spring.jpa.Linea;
import uy.gub.imm.spring.repositorios.custom.LineaRepositorioCustom;

public interface LineaRepositorio extends JpaRepository<Linea, Long>, LineaRepositorioCustom {

	public Linea findByNombre(String nombre);

}
