package com.sistema.pos.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SuscriptorDTO {
    private String nombre;
    private Date fecha_inicio;
    private Date fecha_final;
    private Boolean estado;
    private Long id_usuario; // Solo el ID para evitar cargar toda la entidad Usuario
    private Long id_plan; // Solo el ID para evitar cargar toda la entidad Plan
}
