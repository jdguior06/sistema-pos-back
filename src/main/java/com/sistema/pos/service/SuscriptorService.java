package com.sistema.pos.service;

import com.sistema.pos.dto.PlanDTO;
import com.sistema.pos.dto.UsuarioDTO;
import com.sistema.pos.entity.Plan;
import com.sistema.pos.entity.Rol;
import com.sistema.pos.entity.Suscriptor;
import com.sistema.pos.entity.Usuario;
import com.sistema.pos.repository.PlanRepository;
import com.sistema.pos.repository.RolRepository;
import com.sistema.pos.repository.SuscriptorRepository;
import com.sistema.pos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    @Transactional
    public Suscriptor crearSuscriptor(UsuarioDTO usuarioDTO, PlanDTO planDTO) {

        // Paso 1: Buscar o crear el rol de "ADMINISTRADOR"
        Rol rolAdmin = rolRepository.findByNombre("ADMINISTRADOR")
                .orElseGet(() -> rolRepository.save(new Rol("ADMINISTRADOR")));

        // Paso 2: Registrar al usuario y asignarle el rol de administrador si no existe
        Usuario usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail())
                .orElseGet(() -> {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setNombre(usuarioDTO.getNombre());
                    nuevoUsuario.setApellido(usuarioDTO.getApellido());
                    nuevoUsuario.setEmail(usuarioDTO.getEmail());
                    nuevoUsuario.setPassword(usuarioDTO.getPassword());

                    // Asignar el rol de administrador
                    Set<Rol> roles = new HashSet<>();
                    roles.add(rolAdmin);
                    nuevoUsuario.setRol(roles);

                    return usuarioRepository.save(nuevoUsuario);
                });

        // Paso 3: Verificar el tipo de plan y calcular la fecha final
        Plan plan = planRepository.findByNombre(planDTO.getNombre())
                .orElseThrow(() -> new RuntimeException("El plan no existe."));

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFinal;

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

        // Paso 4: Crear y guardar el suscriptor
        Suscriptor suscriptor = new Suscriptor();
        suscriptor.setNombre(usuario.getNombre());
        suscriptor.setFecha_inicio(java.sql.Date.valueOf(fechaInicio));
        suscriptor.setFecha_final(java.sql.Date.valueOf(fechaFinal));
        suscriptor.setEstado(true);
        suscriptor.setId_usuario(usuario);
        suscriptor.setId_plan(plan);

        return suscriptorRepository.save(suscriptor);
    }
}
