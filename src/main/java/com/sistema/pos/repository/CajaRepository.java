package com.sistema.pos.repository;

import com.sistema.pos.entity.Caja;
import com.sistema.pos.entity.Proveedor;
import com.sistema.pos.entity.Sucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {

    @Query("SELECT p FROM Caja p WHERE " +
            "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Caja> buscarCajas(@Param("searchTerm") String searchTerm);

}

