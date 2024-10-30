package com.sistema.pos.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.FetchProfileOverride;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "suscriptor")
public class Suscriptor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio", nullable = false)
    private Date fecha_inicio;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_final", nullable = false)
    private Date fecha_final;

    @Column(nullable = false)
    private Boolean estado;

    // Relaciones
    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario id_usuario;

    @ManyToOne
    @JoinColumn(name = "id_plan", nullable = false)
    private Plan id_plan;
}