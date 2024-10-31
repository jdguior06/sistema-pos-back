package com.sistema.pos.dto;

import com.sistema.pos.enums.TipoReporte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReporteFiltroDTO {
    private TipoReporte tipoReporte;
    private Date fechaInicio;
    private Date fechaFin;

    private Long  proveedor; //filtro para nota entrada
    private String almacen; //filtro para productoalmacen
    private String producto;
    private String categoria; //filtro para producto
    private Double totalMin;  // Límite mínimo para total
    private Double totalMax;  // Límite máximo para total
    private Integer stockMin; // Límite mínimo para el stock
    private Integer stockMax; // Límite máximo para el stock

}
