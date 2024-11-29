package com.sistema.pos.controller;

import com.sistema.pos.dto.NotaEntradaCompletoDTO;
import com.sistema.pos.entity.Nota_Entrada;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.NotaEntradaService;
import com.sistema.pos.util.HttpStatusMessage;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notaEntrada")
public class NotaEntradaController {

    @Autowired
    private NotaEntradaService notaEntradaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Nota_Entrada>>> obtenerTodasLasNotas() {
        List<Nota_Entrada> notas = notaEntradaService.obtenerTodasLasNotas();
        return new ResponseEntity<>(
                ApiResponse.<List<Nota_Entrada>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(notas)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Nota_Entrada>> obtenerNotaPorId(@PathVariable Long id) {
        try {
            Nota_Entrada nota = notaEntradaService.obtenerNotaPorId(id);
            return new ResponseEntity<>(
                    ApiResponse.<Nota_Entrada>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(nota)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Nota_Entrada>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Nota_Entrada>> crearNotaEntrada(@Valid @RequestBody NotaEntradaCompletoDTO notaEntradaCompletaDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(
                    ApiResponse.<Nota_Entrada>builder()
                            .errors(errors)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        try {
            Nota_Entrada nuevaNota = notaEntradaService.guardarNota(notaEntradaCompletaDto);
            return new ResponseEntity<>(
                    ApiResponse.<Nota_Entrada>builder()
                            .statusCode(HttpStatus.CREATED.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                            .data(nuevaNota)
                            .build(),
                    HttpStatus.CREATED
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Nota_Entrada>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }
}
