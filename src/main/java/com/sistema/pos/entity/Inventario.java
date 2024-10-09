package com.sistema.pos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "inventario")
public class Inventario implements Serializable {
    @EmbeddedId
    private InventarioId id = new InventarioId();

    @ManyToOne
    @MapsId("sucursalId")
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private int stock;
}