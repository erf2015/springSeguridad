package uy.gub.imm.spring.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uy.gub.imm.spring.jpa.StmEntidad;

@Repository
public interface StmEntidadServicio extends CrudRepository<StmEntidad, Long> {

}
