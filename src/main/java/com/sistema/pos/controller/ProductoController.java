package com.sistema.pos.controller;

import com.sistema.pos.dto.Ecommerce;
import com.sistema.pos.dto.ProductoDTO;
import com.sistema.pos.entity.Categoria;
import com.sistema.pos.entity.Producto;
import com.sistema.pos.entity.ProductoAlmacen;
import com.sistema.pos.service.CategoriaService;
import com.sistema.pos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    // Listar productos
    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.findAll());
    }

    // Crear producto con DTO
    @PostMapping("/crear")
    public ResponseEntity<Producto> crearProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            Categoria categoria = categoriaService.findById(productoDTO.getId_categoria())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

            Producto producto = new Producto();
            producto.setNombre(productoDTO.getNombre());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setId_categoria(categoria);
            producto.setFoto(productoDTO.getFoto());  // URL de la imagen

            Producto productoGuardado = productoService.save(producto);
            return ResponseEntity.ok(productoGuardado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        try {
            Optional<Producto> productoExistente = productoService.findById(id);
            return productoExistente.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener productos para la vista Ecommerce
    @GetMapping("/ecommerce")
    public ResponseEntity<List<Ecommerce>> traerTotal() {
        try {
            List<Producto> productos = productoService.findAll();
            List<Ecommerce> lista = productos.stream().map(p -> {
                Ecommerce nuevo = new Ecommerce();
                nuevo.setDescripcion(p.getDescripcion());
                nuevo.setNombre(p.getNombre());
                nuevo.setId_categoria(p.getId_categoria().getId());
                nuevo.setPrecio(p.getPrecio());

                // Calcular la cantidad total
                int cantidad = p.getId_producto_almacen().stream()
                        .mapToInt(ProductoAlmacen::getCantidad)
                        .sum();
                nuevo.setCantidad(cantidad);

                return nuevo;
            }).toList();

            return ResponseEntity.ok(lista);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable("id") Long id,
                                                   @RequestBody ProductoDTO productoDTO) {
        try {
            Optional<Producto> productoExistente = productoService.findById(id);
            if (productoExistente.isPresent()) {
                Producto producto = productoExistente.get();
                Categoria categoria = categoriaService.findById(productoDTO.getId_categoria())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

                producto.setNombre(productoDTO.getNombre());
                producto.setPrecio(productoDTO.getPrecio());
                producto.setDescripcion(productoDTO.getDescripcion());
                producto.setId_categoria(categoria);
                producto.setFoto(productoDTO.getFoto());  // URL de la imagen

                Producto productoActualizado = productoService.save(producto);
                return ResponseEntity.ok(productoActualizado);

            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        try {
            if (productoService.findById(id).isPresent()) {
                productoService.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
