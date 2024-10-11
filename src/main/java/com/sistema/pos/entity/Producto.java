package com.sistema.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private  String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private String foto;




    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria id_categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "id_producto", cascade = CascadeType.ALL)
    private List<ProductoAlmacen> id_producto_almacen;
}
