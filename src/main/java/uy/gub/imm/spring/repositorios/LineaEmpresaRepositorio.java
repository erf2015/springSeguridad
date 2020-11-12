package uy.gub.imm.spring.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.gub.imm.spring.jpa.LineaEmpresa;
import uy.gub.imm.spring.jpa.LineaEmpresaPK;

@Repository
public interface LineaEmpresaRepositorio extends JpaRepository<LineaEmpresa, LineaEmpresaPK> {

}
