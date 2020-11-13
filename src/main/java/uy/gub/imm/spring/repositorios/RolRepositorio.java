package uy.gub.imm.spring.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.gub.imm.spring.jpa.Rol;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long> {

	Optional<Rol> findByAuthority(String authority);
}
