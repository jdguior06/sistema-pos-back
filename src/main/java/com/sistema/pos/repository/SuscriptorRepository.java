package com.sistema.pos.repository;

import com.sistema.pos.entity.Suscriptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuscriptorRepository extends JpaRepository<Suscriptor,Long> {

    Optional<Suscriptor> findById(Long id);
    Optional<Suscriptor>findByNombre(String nombre);
    Optional<Suscriptor>findByEstado(boolean estado);
}
