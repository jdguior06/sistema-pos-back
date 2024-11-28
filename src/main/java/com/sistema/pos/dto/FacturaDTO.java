package com.sistema.pos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class FacturaDTO {
    @Min(value = 0, message = "El total no puede ser negativo")
    private float total;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

    @Min(value = 0, message = "El impuesto no puede ser negativo")
    private float impuesto;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "La moneda es obligatoria")
    private Long id_moneda;

    private Long id_pedido; // Opcional, solo se usa si hay pedido asociado

    private Long id_cliente; // Obligatorio solo si no hay pedido asociado

    @NotEmpty(message = "Debe contener al menos un detalle")
    private List<DetalleVentaDTO> detalle;
}
