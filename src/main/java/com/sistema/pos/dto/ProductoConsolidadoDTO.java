package com.sistema.pos.dto;

import com.sistema.pos.entity.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoConsolidadoDTO {
	
	private Producto producto;
    
    private Integer totalStock;

}
