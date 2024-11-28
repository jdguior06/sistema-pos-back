package com.sistema.pos.service;

import com.sistema.pos.dto.DetalleVentaDTO;
import com.sistema.pos.entity.Detalle_Venta;
import com.sistema.pos.entity.Factura;
import com.sistema.pos.entity.Producto;
import com.sistema.pos.repository.DetalleVentaRepository;
import com.sistema.pos.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetalleVentaService {
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private FacturaService facturaService;
    @Autowired
    private ProductoService productoService;

    public List<Detalle_Venta> findAll() {
        return detalleVentaRepository.findAll();
    }

    public Detalle_Venta findById(Long id) {
    Optional<Detalle_Venta> detalleVenta = detalleVentaRepository.findById(id);
    if (!detalleVenta.isPresent()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el detalle con el id" + id
        );
    }
    return detalleVenta.get();
    }

    public List<DetalleVentaDTO> obtenerDetallesPorFactura(Long idFactura) {
        List<Detalle_Venta> detalles = detalleVentaRepository.findByFacturaId(idFactura);
        return detalles.stream().map(detalle -> {
            DetalleVentaDTO dto = new DetalleVentaDTO();
            dto.setCantidad(detalle.getCantidad());
            dto.setCostoUnitario(detalle.getCostoUnitario());
            dto.setSubTotal(detalle.getSubTotal());
            dto.setDescuento(detalle.getDescuento());
            dto.setId_producto(detalle.getProducto().getId());
            dto.setId_factura(detalle.getFactura().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    public Detalle_Venta save(DetalleVentaDTO detalleVentaDTO) {
        Detalle_Venta detalleVenta = new Detalle_Venta();
        detalleVenta.setCantidad(detalleVentaDTO.getCantidad());
        detalleVenta.setCostoUnitario(detalleVentaDTO.getCostoUnitario());
        detalleVenta.setSubTotal(detalleVentaDTO.getSubTotal());
        detalleVenta.setDescuento(detalleVentaDTO.getDescuento());
        detalleVenta.setActivo(true);

        // Aquí deberías obtener las entidades relacionadas (Producto y Factura)
        // Puedes hacerlo usando un servicio como ProductoService y FacturaService
        Producto producto = productoService.obtenerProducto(detalleVentaDTO.getId_producto());
        Factura factura = facturaService.findById(detalleVentaDTO.getId_factura());

        detalleVenta.setProducto(producto);
        detalleVenta.setFactura(factura);

        return detalleVentaRepository.save(detalleVenta);
    }

    public Detalle_Venta update(Long id, DetalleVentaDTO detalleVentaDTO) {
        Detalle_Venta detalleVenta = findById(id); // Buscar el detalle existente
        detalleVenta.setCantidad(detalleVentaDTO.getCantidad());
        detalleVenta.setCostoUnitario(detalleVentaDTO.getCostoUnitario());
        detalleVenta.setSubTotal(detalleVentaDTO.getSubTotal());
        detalleVenta.setDescuento(detalleVentaDTO.getDescuento());

        Producto producto = productoService.obtenerProducto(detalleVentaDTO.getId_producto());
        Factura factura = facturaService.findById(detalleVentaDTO.getId_factura());

        detalleVenta.setProducto(producto);
        detalleVenta.setFactura(factura);

        return detalleVentaRepository.save(detalleVenta);
    }

    public void delete(Long id) {
        Detalle_Venta detalleVenta = findById(id);
        detalleVenta.setActivo(false);
        detalleVentaRepository.save(detalleVenta);
    }

}
