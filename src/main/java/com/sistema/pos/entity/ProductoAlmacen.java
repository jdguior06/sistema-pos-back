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
    @JoinColumn(name = "id_almacen")
    private Almacen id_almacen;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto id_producto;

    /*@OneToMany(mappedBy = "id_producto_almacen", cascade = CascadeType.ALL)
    private List<OrdenProducto> id_orden_productos;
*/
}
