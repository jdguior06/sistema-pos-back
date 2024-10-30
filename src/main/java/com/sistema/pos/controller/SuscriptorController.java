package com.sistema.pos.controller;
import com.sistema.pos.dto.PlanDTO;
import com.sistema.pos.dto.SuscriptorRequestDTO;
import com.sistema.pos.dto.UsuarioDTO;
import com.sistema.pos.entity.Suscriptor;
import com.sistema.pos.service.SuscriptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/suscriptor")
public class SuscriptorController {
    @Autowired
    private SuscriptorService suscriptorService;

    @PostMapping("/crear")
    public ResponseEntity<Suscriptor> crearSuscriptor(@RequestBody SuscriptorRequestDTO requestDTO) {
        Suscriptor suscriptor = suscriptorService.crearSuscriptor(requestDTO.getUsuarioDTO(), requestDTO.getPlanDTO());
        return ResponseEntity.ok(suscriptor);
    }
}