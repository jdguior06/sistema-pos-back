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
    
    @Column(length = 20)
    private String codigo;
    
    @Column(length = 50)
    private String nombre;
    
    @Column(length = 1000)
    private String descripcion;
    
    private double precio;
    
    private String foto;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "id_producto", cascade = CascadeType.ALL)
    private List<ProductoAlmacen> id_producto_almacen;
    
    private boolean activo;
    
}
