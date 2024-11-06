package com.sistema.pos.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {
	
	private Double total;
	
	private Long id_almacen;
	
	private Long id_cliente;
	
	private Long id_caja_sesion;

    private List<DetalleVentaDTO> detalleVentaDTOS;

}
