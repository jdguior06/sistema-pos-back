package com.sistema.pos.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import com.sistema.pos.entity.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "nota_entrada")
public class Nota_Entrada  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Date fecha;
    private float total;
    private float descuento;

    @ManyToOne
    @JoinColumn(name = "id_almacen")
    @JsonIgnore
    private  Almacen almacen;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_proveedor")
    @JsonIgnore
    private Proveedor proveedor;

}
