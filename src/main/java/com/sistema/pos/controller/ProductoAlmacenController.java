package com.sistema.pos.controller;

import com.sistema.pos.entity.ProductoAlmacen;
import com.sistema.pos.service.ProductoAlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/productos-almacen")
public class ProductoAlmacenController {
    @Autowired
    private ProductoAlmacenService productoAlmacenService;

    @GetMapping("/verificar/{idAlmacen}/{idProducto}")
    public ResponseEntity<Boolean> verificarProducto(@PathVariable Long idAlmacen, @PathVariable Long idProducto) {
        boolean existe = productoAlmacenService.existe(idAlmacen, idProducto);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/{idAlmacen}/{idProducto}")
    public ResponseEntity<ProductoAlmacen> traerProducto(@PathVariable Long idAlmacen, @PathVariable Long idProducto) {
        Optional<ProductoAlmacen> producto = productoAlmacenService.obtenerProducto(idAlmacen, idProducto);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}