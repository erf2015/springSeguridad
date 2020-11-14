package uy.gub.imm.spring.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.gub.imm.spring.jpa.StmEntidad;

@Repository
public interface StmEntidadRepositorio extends JpaRepository<StmEntidad, Long> {

}
