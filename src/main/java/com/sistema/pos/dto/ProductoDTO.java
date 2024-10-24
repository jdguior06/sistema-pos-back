package com.sistema.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
	
	private String codigo;
	
    private String nombre;
    
    private String descripcion;
    
    private double precioCompra;
    
    private double precioVenta;
    
    private String foto;
    
    private Long id_categoria;


}
