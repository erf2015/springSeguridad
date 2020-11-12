package uy.gub.imm.spring.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.gub.imm.spring.jpa.SubLinea;

@Repository
public interface SubLineaRepositorio extends JpaRepository<SubLinea, Long> {

}
