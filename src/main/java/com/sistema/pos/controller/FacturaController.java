package com.sistema.pos.controller;

import com.sistema.pos.dto.FacturaDTO;
import com.sistema.pos.entity.Factura;
import com.sistema.pos.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    // Obtener todas las facturas
    @GetMapping
    public ResponseEntity<List<Factura>> obtenerTodasFacturas() {
        try {
            List<Factura> facturas = facturaService.findAll();
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Obtener una factura por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerFacturaPorId(@PathVariable Long id) {
        try {
            Factura factura = facturaService.findById(id);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Factura no encontrada con el id " + id);
        }
    }


    // Actualizar una factura existente
    /*@PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarFactura(@PathVariable Long id, @RequestBody FacturaDTO facturaDTO) {
        try {
            Factura facturaActualizada = facturaService.up(id, facturaDTO);
            return ResponseEntity.ok("Factura actualizada con éxito. ID: " + facturaActualizada.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Factura no encontrada con el id " + id);
        }
    }

    // Eliminar una factura (marcar como inactiva)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarFactura(@PathVariable Long id) {
        try {
            facturaService.Delete(id);
            return ResponseEntity.ok("Factura eliminada con éxito. ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Factura no encontrada con el id " + id);
        }
    }
*/
    // Procesar una venta y crear la factura
    @PostMapping("/procesar")
    public ResponseEntity<?> procesarVenta(@RequestBody FacturaDTO facturaDTO) {
        try {
            // 1. Crear la factura en el sistema
            Factura factura = facturaService.crearFactura(facturaDTO);

            // 2. Procesar la venta (descontar el stock) si se desea
            // facturaService.procesarVenta(factura.getId());

            // 3. Retornar respuesta exitosa
            return ResponseEntity.ok("Venta procesada con éxito. Factura ID: " + factura.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la venta");
        }
    }

}
