package com.sistema.pos.dto;

import lombok.Data;

@Data
public class AlmacenDTO {
    private int numero;
    private String tipo;
    private String descripcion;
    private Long id_sucursal;
}
