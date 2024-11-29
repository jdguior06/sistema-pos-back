package com.sistema.pos.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {
		
	private Long id_cliente;
	
	private Long id_caja_sesion;

    private List<DetalleVentaDTO> detalleVentaDTOS;
    
    private List<MetodoPagoDTO> metodosPago;

}
