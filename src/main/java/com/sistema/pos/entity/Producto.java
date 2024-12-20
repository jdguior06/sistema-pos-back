package com.sistema.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "producto")
@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(length = 20)
	private String codigo;

	@Column(length = 50)
	private String nombre;

	@Column(length = 1000)
	private String descripcion;

	private float precioCompra;

	private float precioVenta;

	private String foto;

	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;

	private boolean activo;

	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<DetalleNotaE> detalles;

}
