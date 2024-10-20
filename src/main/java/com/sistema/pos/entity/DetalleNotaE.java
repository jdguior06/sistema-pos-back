package com.sistema.pos.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_nota_entrada")
public class DetalleNotaE {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int cantidad;
    private float costoUnitario;
    private float subTotal;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto id_producto;

    @ManyToOne
    @JoinColumn(name= "id_nota")
    private Nota_Entrada id_nota;
}
