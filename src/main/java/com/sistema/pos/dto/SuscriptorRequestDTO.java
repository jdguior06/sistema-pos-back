package com.sistema.pos.dto;
import lombok.Data;

@Data
public class SuscriptorRequestDTO {
    private UsuarioDTO usuarioDTO;
    private PlanDTO planDTO;
}