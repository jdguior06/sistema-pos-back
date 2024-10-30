package com.sistema.pos.dto;

import lombok.Data;

@Data
public class TarjetaDTO {

    private String nombre_titular;
    private String numero_tarjeta;
    private String mes_año;
    private String cvc;
    private String correo;

}
