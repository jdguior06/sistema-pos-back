package com.sistema.pos.repository;

import com.sistema.pos.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Long> {

    Optional<Plan> findById(Long id);
    Optional<Plan> findByNombre(String nombre);
    Optional<Plan> findByTipo(String tipo);

}
