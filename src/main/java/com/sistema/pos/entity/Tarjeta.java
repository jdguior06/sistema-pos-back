package com.sistema.pos.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tarjeta")
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nombre_titular", nullable = false, length = 255) // Nombre del titular de la tarjeta
    private String nombre_titular;

    @Column(name = "nro_tarjeta", nullable = false, length = 30, unique = true) // Número de tarjeta único
    private String numero_tarjeta;

    @Column(name = "mes_año", nullable = false, length = 5) // Mes y año de expiración en formato "MM/YY"
    private String mes_año;

    @Column(nullable = false, length = 6) // Código de seguridad
    private String cvc;

    @Column(nullable = false, length = 255) // Correo del titular de la tarjeta
    private String correo;

    // Relación con Suscriptor
    @OneToOne
    @JoinColumn(name = "id_suscriptor",nullable = false)
    private Suscriptor id_suscriptor;

}
