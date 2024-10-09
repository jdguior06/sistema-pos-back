package com.sistema.pos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permiso")
@Entity
public class Permiso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; 

	private String nombre;
	
}
