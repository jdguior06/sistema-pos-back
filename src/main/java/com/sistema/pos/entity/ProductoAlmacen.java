package com.sistema.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "producto_almacen")
public class ProductoAlmacen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int stock;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "almacen_id")
    private Almacen almacen;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

}
