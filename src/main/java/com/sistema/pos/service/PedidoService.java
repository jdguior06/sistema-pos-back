package com.sistema.pos.service;

import com.sistema.pos.dto.PedidoDTO;
import com.sistema.pos.dto.PedidoModiDTO;
import com.sistema.pos.dto.Pedido_ProductoDTO;
import com.sistema.pos.entity.*;
import com.sistema.pos.repository.*;
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
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Pedido> listar(){
        List<Pedido> pedido=pedidoRepository.findAll();
        return pedido;
    }
    @Transactional
    public Pedido crearPedido(PedidoDTO pedidoDTO) {
        // Verificar usuario
        Usuario usuario = usuarioRepository.findByEmail(pedidoDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Verificar cliente (opcional)
        Cliente cliente = null;
        if (pedidoDTO.getId_cliente() != null) {
            cliente = clienteRepository.findById(pedidoDTO.getId_cliente())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        }

        // Verificar productos
        if (pedidoDTO.getDetalle() == null || pedidoDTO.getDetalle().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido debe tener al menos un producto");
        }

        List<Long> productIds = pedidoDTO.getDetalle().stream()
                .map(Pedido_ProductoDTO::getId_producto)
                .toList();

        Map<Long, Producto> productosMap = productoRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Producto::getId, producto -> producto));

        if (productosMap.size() != productIds.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Uno o más productos no existen");
        }

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setFecha(new Date());
        pedido.setEstado(true);
        pedido.setDescripcion(pedidoDTO.getDescripcion());
        pedido.setUsuario(usuario);
        pedido.setCliente(cliente);

        // Calcular total y crear Pedido_Producto
        float total = 0;
        List<Pedido_Producto> pedidoProductos = new ArrayList<>();

        for (Pedido_ProductoDTO detalle : pedidoDTO.getDetalle()) {
            Producto producto = productosMap.get(detalle.getId_producto());

            if (detalle.getCantidal() == null || detalle.getCantidal() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Cantidad inválida para el producto: " + producto.getNombre());
            }

            float subtotal = (float) (producto.getPrecioVenta() * detalle.getCantidal());
            total += subtotal;

            Pedido_Producto pedidoProducto = new Pedido_Producto();
            pedidoProducto.setPedido(pedido);
            pedidoProducto.setProducto(producto);
            pedidoProducto.setCantidad(detalle.getCantidal());
            pedidoProducto.setSubtotal(subtotal);

            pedidoProductos.add(pedidoProducto);
        }

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
    @Transactional
    public void eliminarPedido(Long idPedido) {
        // Buscar el pedido por ID
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        // Verificar el estado del pedido
        if (!pedido.getEstado()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido ya no está activo y no se puede eliminar");
        }

        // Eliminar productos asociados al pedido
        pedidoProductoRepository.deleteAllByPedidoId(idPedido);

        // Eliminar el pedido
        pedidoRepository.deleteById(idPedido);
    }

    @Transactional
    public Map<String, Object> verPedidoConDetalle(Long idPedido) {
        // Buscar el pedido
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        // Obtener el cliente asociado al pedido
        Cliente cliente = pedido.getCliente();

        // Buscar el detalle y mapearlo a un formato personalizado
        List<Map<String, Object>> detalle = pedidoProductoRepository.findByPedidoId(idPedido).stream()
                .map(pedidoProducto -> {
                    Producto producto = pedidoProducto.getProducto();
                    Map<String, Object> detalleProducto = new HashMap<>();
                    detalleProducto.put("id_producto", producto.getId());
                    detalleProducto.put("codigo", producto.getCodigo());
                    detalleProducto.put("nombre", producto.getNombre());
                    detalleProducto.put("precio", producto.getPrecioVenta());
                    detalleProducto.put("cantidad", pedidoProducto.getCantidad());
                    detalleProducto.put("subtotal", pedidoProducto.getSubtotal());
                    return detalleProducto;
                })
                .collect(Collectors.toList());

        // Construir la respuesta
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("pedido", pedido);
        respuesta.put("detalle", detalle);

        // Agregar el NIT del cliente, si existe
        if (cliente != null) {
            respuesta.put("nit_cliente", cliente.getNit());
        } else {
            respuesta.put("nit_cliente", null);
        }

        return respuesta;
    }

    @Transactional
    public Pedido modificarPedido(Long idPedido, PedidoModiDTO pedidoDTO) {
        // Buscar el pedido existente
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        // Actualizar datos del pedido
        pedido.setDescripcion(pedidoDTO.getDescripcion());
        pedido.setEstado(true); // Si se modifican los productos, el pedido se considera activo
        pedido.setFecha(new Date()); // Actualiza la fecha a la actual, si es necesario

        // Si el cliente es proporcionado, actualizarlo
        if (pedidoDTO.getId_cliente() != null) {
            Cliente cliente = clienteRepository.findById(pedidoDTO.getId_cliente())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
            pedido.setCliente(cliente);
        }

        // **Eliminar todos los detalles existentes de pedido_producto**
        pedidoProductoRepository.deleteAllByPedidoId(idPedido);

        // Procesar la nueva lista de detalles
        if (pedidoDTO.getDetalle() == null || pedidoDTO.getDetalle().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido debe tener al menos un producto en el detalle");
        }

        List<Long> productIds = pedidoDTO.getDetalle().stream()
                .map(Pedido_ProductoDTO::getId_producto)
                .toList();

        Map<Long, Producto> productosMap = productoRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Producto::getId, producto -> producto));

        float total = 0;
        List<Pedido_Producto> nuevosDetalles = new ArrayList<>();

        for (Pedido_ProductoDTO detalleDTO : pedidoDTO.getDetalle()) {
            Producto producto = productosMap.get(detalleDTO.getId_producto());
            if (producto == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Producto no encontrado con ID: " + detalleDTO.getId_producto());
            }

            float subtotal = (float) (producto.getPrecioVenta() * detalleDTO.getCantidal());
            total += subtotal;

            Pedido_Producto nuevoDetalle = new Pedido_Producto();
            nuevoDetalle.setPedido(pedido);
            nuevoDetalle.setProducto(producto);
            nuevoDetalle.setCantidad(detalleDTO.getCantidal());
            nuevoDetalle.setSubtotal(subtotal);

            nuevosDetalles.add(nuevoDetalle);
        }

        // Actualizar el total del pedido
        pedido.setToal(total);

        // Guardar el pedido y los nuevos detalles
        pedidoRepository.save(pedido);
        pedidoProductoRepository.saveAll(nuevosDetalles);

        return pedido;
    }



    }

