package com.sistema.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pos.entity.Bitacora;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {

}
