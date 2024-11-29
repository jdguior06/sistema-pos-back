package com.sistema.pos.dto;

import com.sistema.pos.entity.TipoPago;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPagoDTO {
	
	private TipoPago tipoPago;
    private Double monto;
    private String detalles;

}
