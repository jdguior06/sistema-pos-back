package com.sistema.pos.repository;

import com.sistema.pos.entity.Detalle_Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<Detalle_Venta, Long> {
    List<Detalle_Venta> findByFacturaId(Long id_factura);
}
