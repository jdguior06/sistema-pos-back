package com.sistema.pos.controller;

import com.sistema.pos.dto.DetalleNotaDTO;
import com.sistema.pos.dto.ProductoAlmacenDTO;
import com.sistema.pos.dto.ProductoDTO;
import com.sistema.pos.entity.Producto;
import com.sistema.pos.entity.ProductoAlmacen;
import com.sistema.pos.response.ApiResponse;
import com.sistema.pos.service.ProductoAlmacenService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/almacen/{idAlmacen}/productos-almacen")
public class ProductoAlmacenController {
	
    @Autowired
    private ProductoAlmacenService productoAlmacenService;

    // Obtener todos los productos en almacenes
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

//    // Obtener un producto en el almacén por ID
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<ProductoAlmacen>> getProductoAlmace(@PathVariable Long id) {
//        try {
//            ProductoAlmacen productoAlmacen = productoAlmacenService.obtener(id);
//            return new ResponseEntity<>(
//                    ApiResponse.<ProductoAlmacen>builder()
//                            .statusCode(HttpStatus.OK.value())
//                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
//                            .data(productoAlmacen)
//                            .build(),
//                    HttpStatus.OK
//            );
//        } catch (ResponseStatusException e) {
//            return new ResponseEntity<>(
//                    ApiResponse.<ProductoAlmacen>builder()
//                            .statusCode(e.getStatusCode().value())
//                            .message(e.getReason())
//                            .build(),
//                    e.getStatusCode()
//            );
//        }
//    }
//
//    // Guardar o actualizar el stock de un producto en el almacén usando DetalleNotaDTO
//    @PostMapping
//    public ResponseEntity<ApiResponse<ProductoAlmacen>> guardarProductoAlmacen(
//            @Valid @RequestBody ProductoAlmacen productoAlmacen,
//            @RequestBody DetalleNotaDTO detalleNotaDTO,
//            BindingResult bindingResult) {
//
//        // Validación de errores
//        if (bindingResult.hasErrors()) {
//            List<String> errors = bindingResult.getAllErrors().stream()
//                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                    .collect(Collectors.toList());
//            return new ResponseEntity<>(
//                    ApiResponse.<ProductoAlmacen>builder()
//                            .errors(errors)
//                            .build(),
//                    HttpStatus.BAD_REQUEST
//            );
//        }
//
//        try {
//            // Guardar o actualizar el stock de un producto en el almacén
//            ProductoAlmacen producto = productoAlmacenService.save(productoAlmacen, detalleNotaDTO);
//            return new ResponseEntity<>(
//                    ApiResponse.<ProductoAlmacen>builder()
//                            .statusCode(HttpStatus.CREATED.value())
//                            .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
//                            .data(producto)
//                            .build(),
//                    HttpStatus.CREATED
//            );
//        } catch (ResponseStatusException e) {
//            return new ResponseEntity<>(
//                    ApiResponse.<ProductoAlmacen>builder()
//                            .statusCode(e.getStatusCode().value())
//                            .message(e.getReason())
//                            .build(),
//                    e.getStatusCode()
//            );
//        }
//    }

}