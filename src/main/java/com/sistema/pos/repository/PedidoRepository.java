package com.sistema.pos.repository;

import com.sistema.pos.entity.Pedido;
import com.sistema.pos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByUsuarioAndEstadoTrue(Usuario usuario);
}
