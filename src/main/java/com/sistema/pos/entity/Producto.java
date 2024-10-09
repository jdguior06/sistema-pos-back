package com.sistema.pos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "producto")
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String descripcion;
    private String nombre;
    private float precio;
    private String image;

    @OneToMany(mappedBy = "producto")
    private List<Inventario> inventarios;
}
