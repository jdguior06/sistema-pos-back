package com.sistema.pos.repository;

import com.sistema.pos.entity.Pedido_Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Pedido_ProductoRepository extends JpaRepository<Pedido_Producto,Long> {
    void deleteAllByPedidoId(Long pedidoId);

    List<Pedido_Producto> findByPedidoId(Long pedidoId);
}
