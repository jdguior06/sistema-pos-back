package com.sistema.pos.service;

import com.sistema.pos.config.LoggableAction;
import com.sistema.pos.dto.DetalleNotaDTO;
import com.sistema.pos.dto.NotaEntradaCompletoDTO;
import com.sistema.pos.entity.*;
import com.sistema.pos.repository.NotaEntradaRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaEntradaService {

    @Autowired
    private NotaEntradaRepository notaEntradaRepository;

    @Autowired
    private ProductoAlmacenService productoAlmacenService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private AlmacenService almacenService;
    
    @Autowired
    private UsuarioService usuarioService;

    public List<Nota_Entrada> obtenerTodasLasNotas() {
        return notaEntradaRepository.findAll();
    }

    public Nota_Entrada obtenerNotaPorId(Long idNota) {
        return notaEntradaRepository.findById(idNota)
                .orElseThrow(() -> new IllegalArgumentException("Nota de entrada no encontrada"));
    }


   @LoggableAction
   @Transactional
   public Nota_Entrada guardarNota(NotaEntradaCompletoDTO notaEntradaCompletaDto) {
	   
       Proveedor proveedor = proveedorService.obtenerProveedorPorId(notaEntradaCompletaDto.getProveedor());
       
       Almacen almacen = almacenService.obtenerAlmacenId(notaEntradaCompletaDto.getAlmacen());
       
       Usuario usuario = usuarioService.obtenerUsuarioAuthenticado();

       Nota_Entrada notaEntrada = new Nota_Entrada();
       notaEntrada.setProveedor(proveedor);
       notaEntrada.setAlmacen(almacen);
       notaEntrada.setUsuario(usuario);
       
       Double total = 0.00;
       
       List<DetalleNotaE> detalles = new ArrayList<>();
       
       for (DetalleNotaDTO detalle : notaEntradaCompletaDto.getDetalles()) {
    	   
           Producto producto = productoService.obtenerProducto(detalle.getProductoId());

           DetalleNotaE detalleNota = new DetalleNotaE();
           detalleNota.setProducto(producto);
           detalleNota.setCantidad(detalle.getCantidad());
           detalleNota.setCostoUnitario(producto.getPrecioCompra());
           detalleNota.setSubTotal(detalle.getCantidad() * producto.getPrecioCompra());
           detalleNota.setNotaEntrada(notaEntrada);
           
           detalles.add(detalleNota);

           total = total + detalleNota.getSubTotal();

           productoAlmacenService.actualizarOGuardarStock(almacen.getId(), 
        		   producto.getId(), detalle.getCantidad());
           
       }
       
       notaEntrada.setDetalleNotaEntrada(detalles);
       
       notaEntrada.setTotal(total);

       return notaEntradaRepository.save(notaEntrada); 
   }

}
