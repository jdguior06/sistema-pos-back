package com.sistema.pos.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DateTimeException;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pedido_usuario ")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private Float total;
    private Boolean estado;
    private String descripcion;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario",nullable = false)
    private  Usuario usuario;
    @OneToOne
    @JoinColumn(name ="id_cliente",nullable = true)
    private Cliente cliente;

    @OneToOne(mappedBy = "pedido")
    private Factura factura;

    @OneToMany(mappedBy = "pedido",cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Pedido_Producto> detalle;

}
