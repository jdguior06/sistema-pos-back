package com.sistema.pos.controller;

import com.sistema.pos.entity.Inventario;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.InventarioService;
import com.sistema.pos.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Inventario>>> listarInventarios() {
        List<Inventario> inventarios = inventarioService.listInventarios();
        return new ResponseEntity<>(
                ApiResponse.<List<Inventario>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(inventarios)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Inventario>> agregarInventario(@RequestBody Inventario inventario) {
        Inventario nuevoInventario = inventarioService.saveInventario(inventario);
        return new ResponseEntity<>(
                ApiResponse.<Inventario>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoInventario)
                        .build(),
                HttpStatus.CREATED
        );
    }


}
