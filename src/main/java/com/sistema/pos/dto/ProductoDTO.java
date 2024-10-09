package com.sistema.pos.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long id; // Puede ser nulo al crear un nuevo producto

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;

    private String imagen;

    @NotNull(message = "El precio no puede ser nulo")
    private float precio;

    private Long idCategoria;

}
