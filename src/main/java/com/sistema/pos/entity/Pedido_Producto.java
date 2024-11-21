package com.sistema.pos.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido_usuario_producto")
public class Pedido_Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_producto_seq")
    @SequenceGenerator(name = "pedido_producto_seq", sequenceName = "pedido_usuario_producto_seq", allocationSize = 50)
    private Long id;

    private Integer cantidad;
    private Float subtotal;
    @ManyToOne
    @JoinColumn(name = "id_pedido",nullable = false)
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "id_producto",nullable = false)
    private Producto producto;
}
