package com.sistema.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenDTO {
	
    private int numero;

    private String descripcion;
    
    private Long id_sucursal;
    
}
