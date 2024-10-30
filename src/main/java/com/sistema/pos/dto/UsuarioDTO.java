package com.sistema.pos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
	
	private Long id;

    @NotEmpty(message = "ingrese un nombre")
    private String nombre;
    @NotEmpty(message = "ingrese un apellido")
    private String apellido;
    @Email(message = "El email debe ser válido")
    private String email;
    @NotEmpty(message = "ingrese su contraseña")
    private String password;
    private Long rolId;

}
