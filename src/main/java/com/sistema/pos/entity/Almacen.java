package com.sistema.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "almacenes")
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int numero;
    private String descripcion;
private boolean activo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

    @JsonIgnore
    @OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL)
    private List<ProductoAlmacen> productosAlmacen;

    @JsonIgnore // Evitar problemas de referencia circular
    @OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL)

    private List<Proveedor> proveedores; // Relación con Proveedor
}
