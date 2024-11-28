package com.sistema.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_venta")
public class Detalle_Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int cantidad;
    private float costoUnitario;
    private float subTotal;
    private boolean activo;
    private float descuento;
    @ManyToOne
    @JoinColumn(name = "id_producto",nullable = false)
    private Producto producto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_Factura",nullable = false)
    private Factura factura;
}
