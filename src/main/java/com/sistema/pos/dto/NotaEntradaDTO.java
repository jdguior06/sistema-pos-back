package com.sistema.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaEntradaDTO {
    private Long almacen;
    private Date fecha;
    private float descuento;
    private Long proveedor;
    private List<DetalleNotaDTO> detalles;


}
