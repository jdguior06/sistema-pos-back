package com.sistema.pos.service;

import com.sistema.pos.dto.PlanDTO;
import com.sistema.pos.dto.UsuarioDTO;
import com.sistema.pos.entity.Plan;
import com.sistema.pos.entity.Suscriptor;
import com.sistema.pos.entity.Usuario;
import com.sistema.pos.repository.PlanRepository;
import com.sistema.pos.repository.RolRepository;
import com.sistema.pos.repository.SuscriptorRepository;
import com.sistema.pos.repository.UsuarioRepository;
import com.sistema.pos.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Service
public class SuscriptorService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private SuscriptorRepository suscriptorRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public Suscriptor crearSuscriptor(UsuarioDTO usuarioDTO, PlanDTO planDTO) {

        // Paso 1: Crear usuario administrador (o asegurarse de que existe) y obtener el token
        usuarioService.createUserAdmin(usuarioDTO);

        // Buscar al usuario creado por su email para obtener el objeto Usuario completo
        Usuario usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Error al crear el usuario administrador."));

        // Paso 2: Verificar el tipo de plan y calcular la fecha final
        Plan plan = planRepository.findByNombre(planDTO.getNombre())
                .orElseThrow(() -> new RuntimeException("El plan no existe."));

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFinal;

        // Calcular la fecha final según el tipo de plan
        switch (plan.getTipo().toLowerCase()) {
            case "mes":
                fechaFinal = fechaInicio.plusDays(30);
                break;
            case "año":
                fechaFinal = fechaInicio.plusYears(1);
                break;
            default:
                throw new RuntimeException("Tipo de plan desconocido: " + plan.getTipo());
        }

        // Paso 3: Crear y guardar el suscriptor
        Suscriptor suscriptor = new Suscriptor();
        suscriptor.setNombre(usuario.getEmail());
        suscriptor.setFecha_inicio(java.sql.Date.valueOf(fechaInicio));
        suscriptor.setFecha_final(java.sql.Date.valueOf(fechaFinal));
        suscriptor.setEstado(true);
        suscriptor.setId_usuario(usuario); // Asignar el usuario al suscriptor
        suscriptor.setId_plan(plan); // Asignar el plan al suscriptor

        return suscriptorRepository.save(suscriptor);
    }
}
