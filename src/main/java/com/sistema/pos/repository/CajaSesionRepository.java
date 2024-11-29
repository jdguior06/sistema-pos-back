package com.sistema.pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistema.pos.entity.CajaSesion;

@Repository
public interface CajaSesionRepository extends JpaRepository<CajaSesion, Long>{
	
    Optional<CajaSesion> findByCajaIdAndAbiertaTrue(Long cajaId);

    Optional<CajaSesion> findFirstByAbiertaTrue();
    
    @Query("SELECT cs FROM CajaSesion cs WHERE cs.caja.id = :cajaId AND cs.abierta = true AND cs.usuario.id = :usuarioId")
    Optional<CajaSesion> findByCajaIdAndUsuarioIdAndAbiertaTrue(@Param("cajaId") Long cajaId, @Param("usuarioId") Long usuarioId);

}
