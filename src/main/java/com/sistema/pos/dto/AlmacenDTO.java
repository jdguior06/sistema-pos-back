package com.sistema.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlmacenDTO {
    private String descripcion;
    private Long id_sucursal;
    private List<Long> proveedorIds;
}
