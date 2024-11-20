package com.sistema.pos.controller;

import com.sistema.pos.dto.PedidoDTO;
import com.sistema.pos.entity.Pedido;
import com.sistema.pos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
