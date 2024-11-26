package com.sistema.pos.controller;

import com.sistema.pos.dto.ProductoAlmacenDTO;
import com.sistema.pos.dto.ProductoDTO;
import com.sistema.pos.entity.Producto;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.ProductoService;
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
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Listar productos
    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> getAllCategorias() {
		List<Producto> productos = productoService.findAll();
		return new ResponseEntity<>(
				ApiResponse.<List<Producto>>builder()
						.statusCode(HttpStatus.OK.value())
						.message(HttpStatusMessage.getMessage(HttpStatus.OK))
						.data(productos)
						.build(),
				HttpStatus.OK
		);
	}

    // Crear producto con DTO
    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> guardarProducto(@Valid @RequestBody ProductoDTO productoDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					ApiResponse.<Producto>builder()
							.errors(errors)
							.build(),
					HttpStatus.BAD_REQUEST
			);
		}
		try {
			Producto producto = productoService.save(productoDTO);
			return new ResponseEntity<>(
					ApiResponse.<Producto>builder()
							.statusCode(HttpStatus.CREATED.value())
							.message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
							.data(producto)
							.build(),
					HttpStatus.CREATED
			);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(
					ApiResponse.<Producto>builder()
							.statusCode(e.getStatusCode().value())
							.message(e.getReason())
							.build(),
					e.getStatusCode()
			);
		}
	}

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> getProducto(@PathVariable Long id) {
		try {
			Producto producto = productoService.obtenerProducto(id);
			return new ResponseEntity<>(
					ApiResponse.<Producto>builder()
							.statusCode(HttpStatus.OK.value())
							.message(HttpStatusMessage.getMessage(HttpStatus.OK))
							.data(producto)
							.build(),
					HttpStatus.OK
			);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(
					ApiResponse.<Producto>builder()
							.statusCode(e.getStatusCode().value())
							.message(e.getReason())
							.build(),
					e.getStatusCode()
			);
		}
	}
	@GetMapping("/por-sucursal")
	public ResponseEntity<List<ProductoAlmacenDTO>> obtenerProductosPorSucursal(@RequestParam Long idSucursal) {
		List<ProductoAlmacenDTO> productos = productoService.obtenerProductosPorSucursal(idSucursal);
		return ResponseEntity.ok(productos);
	}

    // Obtener productos para la vista Ecommerce
//    @GetMapping("/ecommerce")
//    public ResponseEntity<List<Ecommerce>> traerTotal() {
//        try {
//            List<Producto> productos = productoService.findAll();
//            List<Ecommerce> lista = productos.stream().map(p -> {
//                Ecommerce nuevo = new Ecommerce();
//                nuevo.setDescripcion(p.getDescripcion());
//                nuevo.setNombre(p.getNombre());
//                nuevo.setId_categoria(p.getId_categoria().getId());
//                nuevo.setPrecio(p.getPrecio());
//
//                // Calcular la cantidad total
//                int cantidad = p.getId_producto_almacen().stream()
//                        .mapToInt(ProductoAlmacen::getCantidad)
//                        .sum();
//                nuevo.setCantidad(cantidad);
//
//                return nuevo;
//            }).toList();
//
//            return ResponseEntity.ok(lista);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .header("Error-Message", e.getMessage())
//                    .build();
//        }
//    }

    // Actualizar producto
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					ApiResponse.<Producto>builder()
							.errors(errors)
							.build(),
					HttpStatus.BAD_REQUEST
			);
		}
		try {
			Producto productoActualizado = productoService.actualizarProducto(id, productoDTO);
			return new ResponseEntity<>(
					ApiResponse.<Producto>builder()
							.statusCode(HttpStatus.OK.value())
							.message(HttpStatusMessage.getMessage(HttpStatus.OK))
							.data(productoActualizado)
							.build(),
					HttpStatus.OK
			);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(
					ApiResponse.<Producto>builder()
							.statusCode(e.getStatusCode().value())
							.message(e.getReason())
							.build(),
					e.getStatusCode()
			);
		}
	}

    // Eliminar producto
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<ApiResponse<Void>> desactivarProducto(@PathVariable Long id) {
		try {
			productoService.eliminarProducto(id);
			return new ResponseEntity<>(
					ApiResponse.<Void>builder()
							.statusCode(HttpStatus.OK.value())
							.message("Producto desactivado correctamente")
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
