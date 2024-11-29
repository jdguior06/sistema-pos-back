package com.sistema.pos.controller;

import com.sistema.pos.entity.ProductoAlmacen;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.ProductoAlmacenService;
import com.sistema.pos.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/almacen/{idAlmacen}/productos-almacen")
public class ProductoAlmacenController {
	
    @Autowired
    private ProductoAlmacenService productoAlmacenService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoAlmacen>>> getAllProductoAlmacen(@PathVariable Long idAlmacen) {
        List<ProductoAlmacen> productoAlmacens = productoAlmacenService.findAllByAlmacenId(idAlmacen);
        return new ResponseEntity<>(
                ApiResponse.<List<ProductoAlmacen>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productoAlmacens)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoAlmacen>> getProductoAlmace(@PathVariable Long id) {
        try {
            ProductoAlmacen productoAlmacen = productoAlmacenService.obtener(id);
            return new ResponseEntity<>(
                    ApiResponse.<ProductoAlmacen>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(productoAlmacen)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<ProductoAlmacen>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

}