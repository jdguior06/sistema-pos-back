package com.sistema.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaEntradaCompletoDTO {
    private Long almacen;
    private LocalDateTime fecha;
    private float descuento;
    private Long proveedor;
    private List<DetalleNotaDTO> detalles;
}