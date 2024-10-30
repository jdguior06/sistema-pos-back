package com.sistema.pos.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlanDTO {
    private String nombre;
    private String tipo;
    private Float costo;
    private String descripcion;
    private Integer limiteUsuarios;
    private Integer limiteSucursales;

    public PlanDTO(String nombre, String tipo, float costo, String descripcion, Integer limiteUsuarios, Integer limiteSucursales) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.costo = costo;
        this.descripcion = descripcion;
        this.limiteUsuarios = limiteUsuarios;
        this.limiteSucursales = limiteSucursales;
    }



}
