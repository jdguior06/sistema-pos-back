package com.sistema.pos.controller;

import com.sistema.pos.entity.DetalleNotaE;
import com.sistema.pos.entity.Nota_Entrada;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.DetalleNotaService;
import com.sistema.pos.service.NotaEntradaService;
import com.sistema.pos.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/detalleNota")
public class DetalleNotaController {

    @Autowired
    private DetalleNotaService detalleNotaService;

    @Autowired
    private NotaEntradaService notaEntradaService;

    // Obtener todos los detalles por ID de Nota
    @GetMapping("/nota/{idNota}")
    public ResponseEntity<ApiResponse<List<DetalleNotaE>>> obtenerDetallesPorNota(@PathVariable Long idNota) {
        List<DetalleNotaE> detalles = detalleNotaService.obtenerDetallesPorNota(idNota);
        return new ResponseEntity<>(
                ApiResponse.<List<DetalleNotaE>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalles)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener detalle por ID
    @GetMapping("/{idDetalle}")
    public ResponseEntity<ApiResponse<DetalleNotaE>> obtenerDetallePorId(@PathVariable Long idDetalle) {
        DetalleNotaE detalle = detalleNotaService.obtenerDetallesPorId(idDetalle);
        return new ResponseEntity<>(
                ApiResponse.<DetalleNotaE>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalle)
                        .build(),
                HttpStatus.OK
        );
    }


    @PostMapping("/nota/{idNota}/detalle")
    public ResponseEntity<ApiResponse<DetalleNotaE>> guardarDetalle(@PathVariable Long idNota, @Valid @RequestBody DetalleNotaE detalleNota, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(
                    ApiResponse.<DetalleNotaE>builder()
                            .errors(errors)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        Nota_Entrada notaEntrada = notaEntradaService.obtenerNotaPorId(idNota);
        DetalleNotaE nuevoDetalle = detalleNotaService.guardarDetalle(detalleNota, notaEntrada);

        return new ResponseEntity<>(
                ApiResponse.<DetalleNotaE>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoDetalle)
                        .build(),
                HttpStatus.CREATED
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<DetalleNotaE>> actualizarDetalle(@PathVariable Long id, @Valid @RequestBody DetalleNotaE detalleNota, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(
                    ApiResponse.<DetalleNotaE>builder()
                            .errors(errors)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        DetalleNotaE detalleActualizado = detalleNotaService.actualizarDetalle(id, detalleNota);
        return new ResponseEntity<>(
                ApiResponse.<DetalleNotaE>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalleActualizado)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar un detalle por ID

}
