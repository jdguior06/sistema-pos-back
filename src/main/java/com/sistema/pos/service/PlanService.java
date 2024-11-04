package com.sistema.pos.service;

import com.sistema.pos.config.LoggableAction;
import com.sistema.pos.dto.PlanDTO;
import com.sistema.pos.entity.Plan;
import com.sistema.pos.repository.PlanRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;

    public List<Plan>findAll(){
        List<Plan> plans=planRepository.findAll();
        return plans;
    }
    public Plan save(Plan c){
        return planRepository.save(c);
    }

    @Transactional
    @LoggableAction
    public Plan modificarPlan(PlanDTO planDTO) {
        // Busca el plan por nombre en la base de datos
        Optional<Plan> optionalPlan = planRepository.findByNombre(planDTO.getNombre());

        if (optionalPlan.isPresent()) {
            // Si el plan existe, actualiza sus datos
            Plan plan = optionalPlan.get();
            plan.setCosto(planDTO.getCosto());
            plan.setDescripcion(planDTO.getDescripcion());
            plan.setLimite_usuarios(planDTO.getLimiteUsuarios());
            plan.setLimite_sucursales(planDTO.getLimiteSucursales());
            plan.setTipo(planDTO.getTipo());

            // Guarda los cambios en la base de datos
            return planRepository.save(plan);
        } else {
            throw new RuntimeException("Plan no encontrado con el nombre: " + planDTO.getNombre());
        }
    }
}
