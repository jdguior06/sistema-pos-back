package com.sistema.pos.repository;


import com.sistema.pos.entity.Inventario;
import com.sistema.pos.entity.InventarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, InventarioId> {
}
