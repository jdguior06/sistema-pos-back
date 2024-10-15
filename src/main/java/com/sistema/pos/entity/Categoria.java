package com.sistema.pos.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Table (name= "categoria")
@Entity
public class Categoria {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(length = 45, nullable = false, unique = true)
    private String nombre;
    
    @Column(length = 1000)
    private String descripcion;
    
    private boolean activo;

}
