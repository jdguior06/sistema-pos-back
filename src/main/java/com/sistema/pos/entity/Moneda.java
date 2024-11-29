package com.sistema.pos.entity;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido_usuario ")
public class Moneda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String moneda;
private float cambio;
private boolean activo;
}
