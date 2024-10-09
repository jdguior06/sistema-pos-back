package com.sistema.pos.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@Table(name = "reporte_sucursal")

public class Reporte_Sucursal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Nro;


        private LocalDate fechaInicio;
        private LocalDate fechaFinal;


        @ManyToOne
        @JoinColumn(name = "producto_id")
        private Producto producto;


        @ManyToOne
        @JoinColumn(name = "sucursal_id")
        private Sucursal sucursal;

        // Getters y setters


    public Reporte_Sucursal() {}
}
