package com.sistema.pos.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor 
public class DetalleVentaDTO {
	
	private Long id_producto;

    private Integer cantidad;

}
