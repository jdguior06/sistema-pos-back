package com.sistema.pos.controller;

import com.sistema.pos.dto.DetalleNotaDTO;
import com.sistema.pos.dto.NotaEntradaCompletoDTO;
import com.sistema.pos.dto.NotaEntradaDTO;
import com.sistema.pos.entity.Nota_Entrada;
import com.sistema.pos.service.NotaEntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notaEntrada")
public class NotaEntradaController {

    @Autowired
    private NotaEntradaService notaEntradaService;

    // Endpoint para obtener todas las notas de entrada
    @GetMapping
    public ResponseEntity<List<Nota_Entrada>> obtenerTodasLasNotas() {
        List<Nota_Entrada> notas = notaEntradaService.obtenerTodasLasNotas();
        return ResponseEntity.ok(notas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Nota_Entrada> obtenerNotaPorId(@PathVariable Long id) {
        Nota_Entrada nota = notaEntradaService.obtenerNotaPorId(id);
        return ResponseEntity.ok(nota);
    }


    @PostMapping
    public ResponseEntity<Nota_Entrada> crearNotaEntrada(@RequestBody NotaEntradaCompletoDTO notaEntradaCompletaDto) {
        Nota_Entrada nuevaNota = notaEntradaService.guardarNota(notaEntradaCompletaDto);
        return ResponseEntity.ok(nuevaNota);
    }


    // Endpoint para eliminar una nota de entrada
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNota(@PathVariable Long id) {
        notaEntradaService.eliminarNota(id);
        return ResponseEntity.noContent().build();
    }
}
