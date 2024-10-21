package com.sistema.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaEntradaDTO {
    private Long idNota;
    private Date fecha;
    private float total;
    private float descuento;
    private Long proveedor;


}
