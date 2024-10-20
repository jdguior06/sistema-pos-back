package com.sistema.pos.repository;

import com.sistema.pos.entity.DetalleNotaE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleNotaRepository extends JpaRepository <DetalleNotaE, Long> {
}
