package com.sistema.pos.dto;

import com.sistema.pos.entity.Almacen;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class SucursalDTO {
    private String codigo;
    private String nit;
    private String nombre;
    private String razonSocial;
    private String direccion;

}
