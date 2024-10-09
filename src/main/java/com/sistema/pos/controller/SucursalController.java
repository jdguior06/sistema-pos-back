package com.sistema.pos.controller;

import com.sistema.pos.entity.Sucursal;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.SucursalService;
import com.sistema.pos.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Sucursal>>> listarSucursales() {
        List<Sucursal> sucursales = sucursalService.listSucursales();
        return new ResponseEntity<>(
                ApiResponse.<List<Sucursal>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(sucursales)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Sucursal>> obtenerSucursal(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.obtenerSucursalPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<Sucursal>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(sucursal)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Sucursal>> agregarSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.saveSucursal(sucursal);
        return new ResponseEntity<>(
                ApiResponse.<Sucursal>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevaSucursal)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarSucursal(@PathVariable Long id) {
        sucursalService.deleteSucursal(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
