package com.sistema.pos.repository;

import com.sistema.pos.entity.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {

    @Query("SELECT a FROM Almacen a WHERE a.sucursal.id = :sucursal")
    Optional<Almacen> findByIdSucursal(@Param("sucursal") Long sucursal);
}
