package com.sistema.pos.controller;

import com.sistema.pos.dto.DetalleNotaDTO;
import com.sistema.pos.dto.NotaEntradaDTO;
import com.sistema.pos.entity.Nota_Entrada;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.NotaEntradaService;
import com.sistema.pos.util.HttpStatusMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/notaEntrada")
public class NotaEntradaController {

    @Autowired
    private NotaEntradaService notaEntradaService;

    // Obtener todas las notas de entrada
    @GetMapping
    public ResponseEntity<ApiResponse<List<Nota_Entrada>>> obtenerTodasLasNotas() {
        List<Nota_Entrada> notas = notaEntradaService.obtenerTodasLasNotas();
        return new ResponseEntity<>(ApiResponse.<List<Nota_Entrada>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                .data(notas)
                .build(), HttpStatus.OK);
    }

    // Obtener una nota de entrada por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Nota_Entrada>> obtenerNotaPorId(@PathVariable Long id) {
        Nota_Entrada nota = notaEntradaService.obtenerNotaPorId(id);
        return new ResponseEntity<>(ApiResponse.<Nota_Entrada>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                .data(nota)
                .build(), HttpStatus.OK);
    }

    // Guardar una nueva nota de entrada
    @PostMapping
    public ResponseEntity<ApiResponse<Nota_Entrada>> guardarNota(
            @Valid @RequestBody NotaEntradaDTO notaEntradaDto,
            @RequestBody List<DetalleNotaDTO> detalles) {
        Nota_Entrada nuevaNota = notaEntradaService.guardarNota(notaEntradaDto, detalles);
        return new ResponseEntity<>(ApiResponse.<Nota_Entrada>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                .data(nuevaNota)
                .build(), HttpStatus.CREATED);
    }

    // Eliminar una nota de entrada por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarNota(@PathVariable Long id) {
        notaEntradaService.eliminarNota(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                .build(), HttpStatus.NO_CONTENT);
    }
}
