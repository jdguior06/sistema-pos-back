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
    
    private String foto;
    
    private Long id_categoria;


}
