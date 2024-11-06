package com.sistema.pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pos.entity.CajaSesion;

@Repository
public interface CajaSesionRepository extends JpaRepository<CajaSesion, Long>{
	
    Optional<CajaSesion> findByCajaIdAndAbiertaTrue(Long cajaId);

    Optional<CajaSesion> findFirstByAbiertaTrue();

}
