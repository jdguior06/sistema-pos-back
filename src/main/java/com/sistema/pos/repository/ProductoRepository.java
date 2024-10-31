package com.sistema.pos.repository;

import com.sistema.pos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
/* Filtra por categoría y fechas opcionalmente
    @Query("SELECT p FROM Producto p WHERE " +
            "(:categoria IS NULL OR p.categoria = :categoria) AND " +
            "(:fechaInicio IS NULL OR p.fechaCreacion >= :fechaInicio) AND " +
            "(:fechaFin IS NULL OR p.fechaCreacion <= :fechaFin)")
    List<Producto> findByCategoriaAndFecha(@Param("categoria") String categoria,
                                           @Param("fechaInicio") Date fechaInicio,
                                           @Param("fechaFin") Date fechaFin);

 */
}
