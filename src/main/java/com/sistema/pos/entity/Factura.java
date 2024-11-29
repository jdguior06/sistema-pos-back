package com.sistema.pos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private float total;
    private LocalDateTime fecha;
    private float impuesto;

    private String estado;

 @ManyToOne
    @JoinColumn(name="id_moneda",nullable = false)
    private Moneda moneda;

    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = true)
    private Pedido pedido;
}
