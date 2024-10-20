package com.sistema.pos.repository;

import com.sistema.pos.entity.Nota_Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaEntradaRepository extends JpaRepository<Nota_Entrada,Long> {

}
