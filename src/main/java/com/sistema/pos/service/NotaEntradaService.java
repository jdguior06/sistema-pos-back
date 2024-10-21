package com.sistema.pos.service;

import com.sistema.pos.dto.DetalleNotaDTO;
import com.sistema.pos.dto.NotaEntradaDTO;
import com.sistema.pos.entity.*;
import com.sistema.pos.repository.DetalleNotaRepository;
import com.sistema.pos.repository.NotaEntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    private NotaEntradaService notaEntradaService;
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


    public Nota_Entrada guardarNota(NotaEntradaDTO notaEntradaDto, List<DetalleNotaDTO> detalles) {
        Proveedor proveedor = proveedorService.obtenerProveedorPorId(notaEntradaDto.getProveedor());

        Nota_Entrada notaEntrada = new Nota_Entrada();
        notaEntrada.setFecha(notaEntradaDto.getFecha());
        notaEntrada.setProveedor(proveedor);

        // Primero guardamos la nota de entrada para obtener un ID
        notaEntrada = notaEntradaRepository.save(notaEntrada);

        // Guardar los detalles de la nota
        float total = 0f;

        for (DetalleNotaDTO detalle : detalles) {
            Producto prod = productoService.obtenerProducto(detalle.getProductoId());

            DetalleNotaE detalleNota = new DetalleNotaE();
            detalleNota.setCantidad(detalle.getCantidad());
            detalleNota.setCostoUnitario(detalle.getCostoUnitario());
            detalleNota.setProducto(prod);
            detalleNotaEService.guardarDetalle(detalleNota,notaEntrada);
            // Guardar el detalle de la nota

            // Calcular el subtotal y agregarlo al total
            float subTotal = detalle.getCantidad() * detalle.getCostoUnitario();
            total += subTotal;

            // Actualizar el stock en ProductoAlmacenService
            ProductoAlmacen productoAlmacen = new ProductoAlmacen();
            productoAlmacen.setProducto(detalleNota.getProducto()); // Establecer el producto
            Almacen almacen= almacenService.obtenerAlmacenPorProv(proveedor.getId());

            productoAlmacen.setAlmacen(almacen); // Obtener el ID del almacén
            productoAlmacenService.save(productoAlmacen, detalle); // Asegúrate de que el ProductoAlmacen tenga el ID correcto
        }
        total -= notaEntrada.getDescuento();

        // Establecer el total calculado en la nota
        notaEntrada.setTotal(total);

        return notaEntradaRepository.save(notaEntrada);

    }

    // Eliminar una nota de entrada por su ID
    public void eliminarNota(Long idNota) {
        notaEntradaRepository.deleteById(idNota);
    }

}

