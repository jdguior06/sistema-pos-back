package com.sistema.pos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sistema.pos.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findByEmail(String email);
	boolean existsByEmail(String email);
	//Optional<Usuario> findByEmail(String email);
	
	@Query("SELECT c FROM Usuario c WHERE " +
	           "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	           "LOWER(c.apellido) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	           "LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	List<Usuario> findByNombreOrApellidoOrEmail(@Param("searchTerm") String searchTerm);

}
