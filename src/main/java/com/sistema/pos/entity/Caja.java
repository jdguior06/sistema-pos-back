package com.sistema.pos.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cajas")
@Entity
public class Caja {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private boolean activo;
    
    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

}