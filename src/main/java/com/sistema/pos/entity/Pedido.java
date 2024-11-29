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
    private Float toal;
    private Boolean estado;
    private String descripcion;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario",nullable = false)
    private  Usuario usuario;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido",cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Pedido_Producto> detalle;
}
