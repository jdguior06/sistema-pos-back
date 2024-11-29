
package com.sistema.pos.dto;

import lombok.Data;

import java.util.List;

@Data
public class PedidoModiDTO {

    private String descripcion;
    private Long id_cliente;
    private List<Pedido_ProductoDTO> detalle;
}
