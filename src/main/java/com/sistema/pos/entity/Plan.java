package com.sistema.pos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Entity
@Data
@Table(name = "plan")
public class Plan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 255) // El nombre no puede ser nulo y tiene una longitud máxima de 255 caracteres
    private String nombre;
    @Column(nullable = false, length = 20) // aqui se define si es por mes o año
    private String tipo;

    @Column(nullable = false)
    private Float costo;

    @Column(length = 255) // Descripción con una longitud máxima de 255 caracteres
    private String descripcion;

    @Column(name = "limite_usuarios", nullable = false)
    private Integer limite_usuarios;

    @Column(name = "limite_sucursales", nullable = false)
    private Integer limite_sucursales;

}
