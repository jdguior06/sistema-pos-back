package com.sistema.pos.repository;

import com.sistema.pos.entity.Nota_Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotaEntradaRepository extends JpaRepository<Nota_Entrada,Long> {
    Optional<Nota_Entrada> findById(Long id);
}
