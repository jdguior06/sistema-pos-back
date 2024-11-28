package com.sistema.pos.dto;

import lombok.Data;

@Data
public class DetalleVentaDTO {
    private int cantidad;
    private float costoUnitario;
    private float subTotal;
    private float descuento;
    private Long id_producto;
    private Long id_factura;
}
