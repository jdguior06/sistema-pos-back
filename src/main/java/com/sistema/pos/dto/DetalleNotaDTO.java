package com.sistema.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleNotaDTO {
    private int cantidad;
    private float costoUnitario;
    private Long productoId;
}