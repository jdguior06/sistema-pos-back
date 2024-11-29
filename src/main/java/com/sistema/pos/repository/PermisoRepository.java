package com.sistema.pos.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pos.entity.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long>{
	
	//public List<Permiso> findAll();
	Optional<Permiso> findByNombre(String nombre);
	List<Permiso> findAllByNombreIn(Set<String> nombres);

}
