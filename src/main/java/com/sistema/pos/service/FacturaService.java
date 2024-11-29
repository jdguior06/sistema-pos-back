package com.sistema.pos.service;

import com.sistema.pos.dto.FacturaDTO;
import com.sistema.pos.entity.*;
import com.sistema.pos.repository.DetalleVentaRepository;
import com.sistema.pos.repository.FacturaRepository;
import com.sistema.pos.repository.PedidoRepository;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacturaService {


    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MonedaService monedaService;

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ProductoService productoService;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private ProductoAlmacenService productoAlmacenService;
    @Autowired
    private PedidoRepository pedidoRepository;



    public List<Pedido> listar(){
        List<Pedido> pedido=pedidoRepository.findAll();
        return pedido;
    }

    public Pedido obtenerpedidoId(Long id){
        Optional<Pedido> pedido=pedidoRepository.findById(id);
        if(!pedido.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el pedidoon el id" + id);

        }
        return pedido.get();
    }

    public Factura findById(Long id) {
        Optional<Factura> factura = facturaRepository.findById(id);
        if (!factura.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la factura con el id" + id);

        }
        return factura.get();
    }

    public Factura crearFactura(FacturaDTO facturaDTO) {
        Pedido pedido = null;
        Cliente cliente = null;
        Moneda moneda = monedaService.findById(facturaDTO.getId_moneda());
        Factura facturanueva = new Factura();
        facturanueva.setFecha(facturaDTO.getFecha());
        facturanueva.setImpuesto(facturaDTO.getImpuesto());
        facturanueva.setEstado(facturaDTO.getEstado());
        facturanueva.setMoneda(moneda);

        List<Detalle_Venta> detallesFactura;
        final float[] totalFactura = {0}; // Clase envolvente para mutabilidad

        if (facturaDTO.getId_pedido() != null) {
            // Caso: Factura asociada a un pedido
            pedido = pedidoService.obtenerpedidoId(facturaDTO.getId_pedido());
            cliente = clienteService.obtenerClientePorId(pedido.getCliente().getId());
            facturanueva.setCliente(cliente);
            facturanueva.setPedido(pedido);

            detallesFactura = pedido.getDetalle().stream().map(pedidoProducto -> {
                Detalle_Venta detalle = new Detalle_Venta();
                detalle.setProducto(pedidoProducto.getProducto());
                detalle.setCantidad(pedidoProducto.getCantidad());

                // Calcular el subtotal para el detalle
                float subtotal = pedidoProducto.getProducto().getPrecioVenta() * pedidoProducto.getCantidad();
                detalle.setSubTotal(subtotal);
                totalFactura[0] += subtotal; // Modificación segura

                detalle.setFactura(facturanueva); // Asociar el detalle a la factura
                return detalle;
            }).collect(Collectors.toList());
        } else {
            // Caso: Factura sin pedido asociado
            cliente = clienteService.obtenerClientePorId(facturaDTO.getId_cliente());
            facturanueva.setCliente(cliente);

            detallesFactura = facturaDTO.getDetalle().stream().map(detalleDTO -> {
                Detalle_Venta detalle = new Detalle_Venta();
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setCostoUnitario(detalleDTO.getCostoUnitario());
                detalle.setDescuento(detalleDTO.getDescuento());

                // Calcular el subtotal para el detalle
                float subtotal = (detalleDTO.getCostoUnitario() * detalleDTO.getCantidad()) - detalleDTO.getDescuento();
                detalle.setSubTotal(subtotal);
                totalFactura[0] += subtotal; // Modificación segura

                detalle.setProducto(productoService.obtenerProducto(detalleDTO.getId_producto()));
                detalle.setFactura(facturanueva); // Asociar el detalle a la factura
                return detalle;
            }).collect(Collectors.toList());
        }

        // Aplicar impuesto al total
        totalFactura[0] += totalFactura[0] * (facturanueva.getImpuesto() / 100);
        facturanueva.setTotal(totalFactura[0]);

        // Guardar la factura
        Factura facturaGuardada = facturaRepository.save(facturanueva);

        // Guardar los detalles
        detallesFactura.forEach(detalle -> detalle.setFactura(facturaGuardada)); // Asociar los detalles a la factura guardada
        detalleVentaRepository.saveAll(detallesFactura);

        return facturaGuardada;
    }

}
