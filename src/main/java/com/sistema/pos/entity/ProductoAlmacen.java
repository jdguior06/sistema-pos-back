package com.sistema.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "producto_almacen")
@Entity
public class ProductoAlmacen {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_almacen")
    @JsonIgnore
    private Almacen almacen;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    private Integer stock;
    
}
