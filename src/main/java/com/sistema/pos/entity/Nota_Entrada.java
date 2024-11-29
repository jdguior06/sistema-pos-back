package com.sistema.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "nota_entrada")
public class Nota_Entrada  {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime fecha;
    private float total;
    private float descuento;

    @ManyToOne
    @JoinColumn(name = "id_almacen")
    @JsonIgnore
    private  Almacen almacen;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_proveedor")

    private Proveedor proveedor;

    @OneToMany(mappedBy = "notaId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleNotaE> detalles;

}
