package com.sistema.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pos.entity.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{

}
