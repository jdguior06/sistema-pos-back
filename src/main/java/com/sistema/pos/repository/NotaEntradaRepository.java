package com.sistema.pos.repository;

import com.sistema.pos.entity.Nota_Entrada;
import com.sistema.pos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotaEntradaRepository extends JpaRepository<Nota_Entrada,Long> {
    Optional<Nota_Entrada> findById(Long id);

    // Filtra por proveedor y fechas opcionalmente

List<Nota_Entrada>findByProveedorId(Long proveedorId);
List<Nota_Entrada>findByFecha(LocalDateTime fecha);
List<Nota_Entrada>findByAlmacen_SucursalIdAndAlmacenId(Long sucursalId, Long almacenId);


}
