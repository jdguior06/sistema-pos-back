package com.sistema.pos.controller;

import com.sistema.pos.dto.DetalleNotaDTO;
import com.sistema.pos.entity.DetalleNotaE;
import com.sistema.pos.service.DetalleNotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalle-nota")
public class DetalleNotaController {

    @Autowired
    private DetalleNotaService detalleNotaService;

    // Endpoint para obtener los detalles de una nota por su ID de nota
    @GetMapping("/nota/{idNota}")
    public ResponseEntity<List<DetalleNotaE>> obtenerDetallesPorNota(@PathVariable Long idNota) {
        List<DetalleNotaE> detalles = detalleNotaService.obtenerDetallesPorNota(idNota);
        return ResponseEntity.ok(detalles);
    }

    // Endpoint para obtener un detalle específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<DetalleNotaE> obtenerDetallePorId(@PathVariable Long id) {
        DetalleNotaE detalle = detalleNotaService.obtenerDetallesPorId(id);
        return ResponseEntity.ok(detalle);
    }

    // Endpoint para crear un nuevo detalle
    @PostMapping
    public ResponseEntity<DetalleNotaE> crearDetalle(@RequestBody DetalleNotaDTO detalleNotaDto) {
        DetalleNotaE nuevoDetalle = detalleNotaService.guardarDetalle(new DetalleNotaE(), null); // Ajustar lógica
        return ResponseEntity.ok(nuevoDetalle);
    }

    // Endpoint para eliminar un detalle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long id) {
        detalleNotaService.eliminarDetalle(id);
        return ResponseEntity.noContent().build();
    }
}
