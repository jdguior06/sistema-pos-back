package com.sistema.pos.config;

import com.sistema.pos.dto.PlanDTO;
import com.sistema.pos.entity.Plan;
import com.sistema.pos.repository.PlanRepository;
import com.sistema.pos.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class PlanInitializationService  {

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanService planService;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        Float mes=(float) 12.0;
        Float descuento=(float) 0.9;
        crearPlanSiNoExiste(new PlanDTO("Free mes", "mes", (float) 0.0,"Una sola sucursal,Dos usuarios",1,2));
        crearPlanSiNoExiste(new PlanDTO("Free año", "año", (float) 0.0,"Una sola sucursal,Dos usuarios",1,2));
        crearPlanSiNoExiste(new PlanDTO("Estandar mes", "mes", (float) 9.99,"3 sucursales,20 usuario",20,3));
        crearPlanSiNoExiste(new PlanDTO("Estandar año", "año", (float) ((9.99*mes)*descuento),"3 sucursales, 20 usuarios",20,3));
        crearPlanSiNoExiste(new PlanDTO("Pro mes", "mes", (float) ((24.99*mes)*descuento),"6 sucursales, 50 usuarios",50,6));
        crearPlanSiNoExiste(new PlanDTO("Pro año", "año", (float) ((24.99*mes)*descuento),"6 sucursales, 50 usuarios",50,6));
        crearPlanSiNoExiste(new PlanDTO("Pro Plus mes", "mes", (float) ((49.99*mes)*descuento),"10 sucursales, 100 usuarios",100,10));
        crearPlanSiNoExiste(new PlanDTO("Pro Plus año", "año", (float) ((49.99*mes)*descuento),"10 sucursales, 100 usuarios",100,10));
        crearPlanSiNoExiste(new PlanDTO("Pro Plus Ultra mes", "mes", (float) ((149.99*mes)*descuento),"20 sucursales, 200 usuarios",200,20));
        crearPlanSiNoExiste(new PlanDTO("Pro Plus Ultra año", "año", (float) ((149.99*mes)*descuento),"20 sucursales, 200 usuarios",200,20));
    }
    private void crearPlanSiNoExiste(PlanDTO planDTO) {
        Optional<Plan> planExistente = planRepository.findByNombre(planDTO.getNombre());
        if (planExistente.isEmpty()) {
            Plan nuevoPlan = new Plan();
            nuevoPlan.setNombre(planDTO.getNombre());
            nuevoPlan.setCosto(planDTO.getCosto());
            nuevoPlan.setDescripcion(planDTO.getDescripcion());
            nuevoPlan.setLimite_usuarios(planDTO.getLimiteUsuarios());
            nuevoPlan.setLimite_sucursales(planDTO.getLimiteSucursales());
            nuevoPlan.setTipo(planDTO.getTipo());
            planRepository.save(nuevoPlan);
            System.out.println("Plan creado: " + planDTO.getNombre());
        } else {
            Plan planActualizado = planService.modificarPlan(planDTO); // Usando planService para modificar
            System.out.println("Los planes fueron actualizados con exito ");
        }
    }
}