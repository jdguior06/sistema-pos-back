package com.sistema.pos.controller;

import com.sistema.pos.dto.PedidoDTO;
import com.sistema.pos.dto.PedidoModiDTO;
import com.sistema.pos.entity.Pedido;
import com.sistema.pos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.crearPedido(pedidoDTO);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping()
    public ResponseEntity<List<Pedido>> obtenerPedidosActivos(@RequestParam String email) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosActivosPorUsuario(email);
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.ok("Pedido eliminado exitosamente");
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Map<String, Object>> verPedidoConDetalle(@PathVariable Long id) {
        Map<String, Object> respuesta = pedidoService.verPedidoConDetalle(id);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> modificarPedido(@PathVariable Long id, @RequestBody PedidoModiDTO pedidoDTO) {
        Pedido pedido = pedidoService.modificarPedido(id, pedidoDTO);
        return ResponseEntity.ok(pedido);
    }
}
