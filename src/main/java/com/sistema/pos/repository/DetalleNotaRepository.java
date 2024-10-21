package com.sistema.pos.repository;

import com.sistema.pos.entity.DetalleNotaE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleNotaRepository extends JpaRepository <DetalleNotaE, Long> {
    List<DetalleNotaE> findByProducto_Id(Long productoId);  // Cambia id_producto por producto
    List<DetalleNotaE> findByNotaId_Id(Long notaEntradaId);
}
