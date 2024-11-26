package com.sistema.pos.service;

import com.sistema.pos.dto.PedidoDTO;
import com.sistema.pos.dto.Pedido_ProductoDTO;
import com.sistema.pos.entity.*;
import com.sistema.pos.repository.PedidoRepository;
import com.sistema.pos.repository.Pedido_ProductoRepository;
import com.sistema.pos.repository.ProductoRepository;
import com.sistema.pos.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private Pedido_ProductoRepository pedidoProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Pedido> listar(){
        List<Pedido> pedido=pedidoRepository.findAll();
        return pedido;
    }
    @Transactional
    public Pedido crearPedido(PedidoDTO pedidoDTO) {
        // Buscar el usuario
        Usuario usuario = usuarioRepository.findByEmail(pedidoDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Crear pedido
        Cliente cliente=new Cliente();
        cliente.setId(pedidoDTO.getId_cliente());
        Pedido pedido = new Pedido();
        pedido.setFecha(new Date());
        pedido.setEstado(true);
        pedido.setDescripcion(pedidoDTO.getDescripcion());
        pedido.setUsuario(usuario);
        pedido.setCliente(cliente);

        // Obtener todos los productos en una sola consulta
        List<Long> productIds = pedidoDTO.getDetalle().stream()
                .map(Pedido_ProductoDTO::getId_producto)
                .toList();
        Map<Long, Producto> productosMap = productoRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Producto::getId, producto -> producto));

        // Inicializar total
        float total = 0;

        // Crear lista de Pedido_Producto
        List<Pedido_Producto> pedidoProductos = new ArrayList<>();
        for (Pedido_ProductoDTO detalle : pedidoDTO.getDetalle()) {
            Producto producto = productosMap.get(detalle.getId_producto());
            if (producto == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Producto no encontrado con ID: " + detalle.getId_producto());
            }

            // Calcular subtotal
            float subtotal = (float) (producto.getPrecioVenta() * detalle.getCantidal());
            total += subtotal;

            // Crear Pedido_Producto
            Pedido_Producto pedidoProducto = new Pedido_Producto();
            pedidoProducto.setPedido(pedido);
            pedidoProducto.setProducto(producto);
            pedidoProducto.setCantidad(detalle.getCantidal());
            pedidoProducto.setSubtotal(subtotal);

            pedidoProductos.add(pedidoProducto);
        }

        // Setear total al pedido
        pedido.setToal(total);

        // Guardar pedido y relaciones
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        pedidoProductos.forEach(p -> p.setPedido(pedidoGuardado));
        pedidoProductoRepository.saveAll(pedidoProductos);

        return pedidoGuardado;
    }
    public List<Pedido> obtenerPedidosActivosPorUsuario(String email) {
        // Verificar que el usuario existe
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Obtener pedidos activos
        return pedidoRepository.findByUsuarioAndEstadoTrue(usuario);
    }
}
