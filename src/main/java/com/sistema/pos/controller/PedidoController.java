package com.sistema.pos.controller;

import com.sistema.pos.dto.PedidoDTO;
import com.sistema.pos.entity.Pedido;
import com.sistema.pos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
