package com.sistema.pos.service;

import com.sistema.pos.config.LoggableAction;
import com.sistema.pos.dto.DetalleNotaDTO;
import com.sistema.pos.dto.NotaEntradaCompletoDTO;
import com.sistema.pos.dto.NotaEntradaDTO;
import com.sistema.pos.entity.*;
import com.sistema.pos.repository.NotaEntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaEntradaService {

    @Autowired
    private NotaEntradaRepository notaEntradaRepository;

    @Autowired
    private ProductoAlmacenService productoAlmacenService;

    @Autowired
    private DetalleNotaService detalleNotaEService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private AlmacenService almacenService;

    // Obtener todas las notas de entrada
    public List<Nota_Entrada> obtenerTodasLasNotas() {
        return notaEntradaRepository.findAll();
    }

    // Obtener una nota de entrada por su ID
    public Nota_Entrada obtenerNotaPorId(Long idNota) {
        return notaEntradaRepository.findById(idNota)
                .orElseThrow(() -> new IllegalArgumentException("Nota de entrada no encontrada"));
    }


   /* public Nota_Entrada guardarNota(NotaEntradaDTO notaEntradaDto) {
        Proveedor proveedor = proveedorService.obtenerProveedorPorId(notaEntradaDto.getProveedor());
        Almacen almacen = almacenService.obtenerAlmacenId(notaEntradaDto.getAlmacen());

        Nota_Entrada notaEntrada = new Nota_Entrada();
        notaEntrada.setFecha(notaEntradaDto.getFecha());
        notaEntrada.setProveedor(proveedor);
        notaEntrada.setAlmacen(almacen);

        // Guardar la nota para obtener su ID
        notaEntrada = notaEntradaRepository.save(notaEntrada);

        // Guardar los detalles de la nota y actualizar el stock
        float total = 0f;
        for (DetalleNotaDTO detalle : notaEntradaDto.getDetalles()) {
            Producto producto = productoService.obtenerProducto(detalle.getProductoId());

            DetalleNotaE detalleNota = new DetalleNotaE();
            detalleNota.setCantidad(detalle.getCantidad());
            detalleNota.setCostoUnitario(detalle.getCostoUnitario());
            detalleNota.setProducto(producto);

            // Guardar el detalle de la nota
            detalleNotaEService.guardarDetalle(detalleNota, notaEntrada);

            // Calcular el subtotal y agregarlo al total
            float subTotal = detalle.getCantidad() * detalle.getCostoUnitario();
            total += subTotal;

            // Actualizar el stock del producto en ese almacén
            ProductoAlmacen productoAlmacen = new ProductoAlmacen();
            productoAlmacen.setProducto(detalleNota.getProducto());
            productoAlmacen.setAlmacen(almacen);
            productoAlmacenService.save(productoAlmacen, detalle); // Actualizar el stock
        }

        total -= notaEntradaDto.getDescuento(); // Aplicar descuento
        notaEntrada.setTotal(total);

        return notaEntradaRepository.save(notaEntrada); // Guardar la nota con el total final
    }
/*

    */
   @LoggableAction
   public Nota_Entrada guardarNota(NotaEntradaCompletoDTO notaEntradaCompletaDto) {
       Proveedor proveedor = proveedorService.obtenerProveedorPorId(notaEntradaCompletaDto.getProveedor());
       Almacen almacen = almacenService.obtenerAlmacenId(notaEntradaCompletaDto.getAlmacen());

       Nota_Entrada notaEntrada = new Nota_Entrada();
       notaEntrada.setFecha(notaEntradaCompletaDto.getFecha());
       notaEntrada.setProveedor(proveedor);
       notaEntrada.setAlmacen(almacen);

       // Guardar la nota para obtener su ID
       notaEntrada = notaEntradaRepository.save(notaEntrada);

       // Guardar los detalles de la nota y actualizar el stock
       float total = 0f;
       for (DetalleNotaDTO detalle : notaEntradaCompletaDto.getDetalles()) {
           Producto producto = productoService.obtenerProducto(detalle.getProductoId());

           DetalleNotaE detalleNota = new DetalleNotaE();
           detalleNota.setCantidad(detalle.getCantidad());
           detalleNota.setCostoUnitario(detalle.getCostoUnitario());
           detalleNota.setProducto(producto);

           // Guardar el detalle de la nota
           detalleNotaEService.guardarDetalle(detalleNota, notaEntrada);

           // Calcular el subtotal y agregarlo al total
           float subTotal = detalle.getCantidad() * detalle.getCostoUnitario();
           total += subTotal;

           // Actualizar el stock del producto en ese almacén
           ProductoAlmacen productoAlmacen = new ProductoAlmacen();
           productoAlmacen.setProducto(detalleNota.getProducto());
           productoAlmacen.setAlmacen(almacen);
          productoAlmacenService.save(productoAlmacen, detalle); // Actualizar el stock
       }

       total -= notaEntradaCompletaDto.getDescuento(); // Aplicar descuento
       notaEntrada.setTotal(total);

       return notaEntradaRepository.save(notaEntrada); // Guardar la nota con el total final
   }


    // Eliminar una nota de entrada por su ID
   	@LoggableAction
    public void eliminarNota(Long idNota) {
        notaEntradaRepository.deleteById(idNota);
    }
}
