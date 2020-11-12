package uy.gub.imm.spring.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.gub.imm.spring.jpa.Institucion;

@Repository
public interface InstitucionesRepositorio extends JpaRepository<Institucion, Long> {

}
