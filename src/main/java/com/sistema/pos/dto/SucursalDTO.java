package com.sistema.pos.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {
	
    
    @Column(length = 20)
    private String codigo;
    
    @Column(length = 20)
    private String nit;
    
    private String nombre;
    
    private String razon_social;
    
    private String direccion;

}
