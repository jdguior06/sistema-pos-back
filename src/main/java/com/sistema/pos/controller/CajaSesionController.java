package com.sistema.pos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sistema.pos.dto.CajaSesionDTO;
import com.sistema.pos.entity.CajaSesion;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.CajaSesionService;
import com.sistema.pos.util.HttpStatusMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/caja-sesion")
public class CajaSesionController {
	
	@Autowired
    private CajaSesionService cajaSesionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CajaSesion>>> listarCajasSesiones() {
        List<CajaSesion> sesiones = cajaSesionService.listarCajasSesiones();
        return new ResponseEntity<>(
                ApiResponse.<List<CajaSesion>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(sesiones)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CajaSesion>> obtenerCajaSesion(@PathVariable Long id) {
        try {
            CajaSesion cajaSesion = cajaSesionService.obtenerCajaSesion(id);
            return new ResponseEntity<>(
                    ApiResponse.<CajaSesion>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(cajaSesion)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<CajaSesion>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    @PostMapping("/apertura")
    public ResponseEntity<ApiResponse<CajaSesion>> aperturaDeCaja(@Valid @RequestBody CajaSesionDTO cajaSesionDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(
                    ApiResponse.<CajaSesion>builder()
                            .errors(errors)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        try {
            CajaSesion cajaSesion = cajaSesionService.aperturaDeCaja(cajaSesionDTO);
            return new ResponseEntity<>(
                    ApiResponse.<CajaSesion>builder()
                            .statusCode(HttpStatus.CREATED.value())
                            .message("Caja abierta correctamente")
                            .data(cajaSesion)
                            .build(),
                    HttpStatus.CREATED
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<CajaSesion>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    @PatchMapping("/{id}/cierre")
    public ResponseEntity<ApiResponse<CajaSesion>> cierreDeCaja(@PathVariable Long id) {
        try {
            CajaSesion cajaSesion = cajaSesionService.cierreDeCaja(id);
            return new ResponseEntity<>(
                    ApiResponse.<CajaSesion>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Caja cerrada correctamente")
                            .data(cajaSesion)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<CajaSesion>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

}
