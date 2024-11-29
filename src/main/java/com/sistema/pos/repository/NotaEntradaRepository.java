package com.sistema.pos.repository;

import com.sistema.pos.entity.Nota_Entrada;
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

    @Query("SELECT n FROM Nota_Entrada n WHERE " +
            "(:proveedor IS NULL OR n.proveedor.id = :proveedor) AND " +
            "(:fechaInicio IS NULL OR n.fecha >= :fechaInicio) AND " +
            "(:fechaFin IS NULL OR n.fecha <= :fechaFin) AND " +
            "(:totalMin IS NULL OR n.total >= :totalMin) AND " +
            "(:totalMax IS NULL OR n.total <= :totalMax)")
    
    List<Nota_Entrada> findByProveedorAndFechaAndTotal(
            @Param("proveedor") Long proveedor,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin,
            @Param("totalMin") Double totalMin,
            @Param("totalMax") Double totalMax
    );

List<Nota_Entrada>findByProveedorId(Long proveedorId);
List<Nota_Entrada>findByFecha(LocalDateTime fecha);
List<Nota_Entrada>findByAlmacen_SucursalIdAndAlmacenId(Long sucursalId, Long almacenId);


}
