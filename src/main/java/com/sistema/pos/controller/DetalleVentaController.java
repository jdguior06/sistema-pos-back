package com.sistema.pos.controller;
import com.sistema.pos.dto.DetalleVentaDTO;
import com.sistema.pos.entity.Detalle_Venta;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.DetalleVentaService;
import com.sistema.pos.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/detalle-venta")
public class DetalleVentaController {
    @Autowired
    private DetalleVentaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Detalle_Venta>>> listarDetallesVenta() {
        List<Detalle_Venta> detalles = service.findAll();
        return new ResponseEntity<>(
                ApiResponse.<List<Detalle_Venta>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalles)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Detalle_Venta>> obtenerDetalleVenta(@PathVariable Long id) {
        try {
            Detalle_Venta detalleVenta = service.findById(id);
            return new ResponseEntity<>(
                    ApiResponse.<Detalle_Venta>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(detalleVenta)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Detalle_Venta>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    @GetMapping("/factura/{idFactura}")
    public ResponseEntity<ApiResponse<List<DetalleVentaDTO>>> obtenerDetallesPorFactura(@PathVariable Long idFactura) {
        List<DetalleVentaDTO> detalles = service.obtenerDetallesPorFactura(idFactura);
        return new ResponseEntity<>(
                ApiResponse.<List<DetalleVentaDTO>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalles)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Detalle_Venta>> guardarDetalleVenta(@Valid @RequestBody DetalleVentaDTO detalleVentaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(
                    ApiResponse.<Detalle_Venta>builder()
                            .errors(errors)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        try {
            Detalle_Venta nuevoDetalleVenta = service.save(detalleVentaDTO);
            return new ResponseEntity<>(
                    ApiResponse.<Detalle_Venta>builder()
                            .statusCode(HttpStatus.CREATED.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                            .data(nuevoDetalleVenta)
                            .build(),
                    HttpStatus.CREATED
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Detalle_Venta>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Detalle_Venta>> actualizarDetalleVenta(@PathVariable Long id, @Valid @RequestBody DetalleVentaDTO detalleVentaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(
                    ApiResponse.<Detalle_Venta>builder()
                            .errors(errors)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        try {
            Detalle_Venta detalleVentaActualizado = service.update(id, detalleVentaDTO);
            return new ResponseEntity<>(
                    ApiResponse.<Detalle_Venta>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(detalleVentaActualizado)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Detalle_Venta>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<ApiResponse<Void>> desactivarDetalleVenta(@PathVariable Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(
                    ApiResponse.<Void>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Detalle de venta desactivado correctamente")
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Void>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }
}
