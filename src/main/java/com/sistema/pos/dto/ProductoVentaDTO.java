package com.sistema.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoVentaDTO {
    private String nombre;
    private String descripcion;
    private Long id_producto;
    private int stock;
    private double precioVenta;

}
