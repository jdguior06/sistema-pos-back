package com.sistema.pos.dto;

import com.sistema.pos.entity.Producto;
import lombok.Data;

import java.util.List;
@Data
public class Pedido_ProductoDTO {
    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }

    public Integer getCantidal() {
        return cantidal;
    }

    public void setCantidal(Integer cantidal) {
        this.cantidal = cantidal;
    }

    private Long id_producto;
    private Integer cantidal;
}
