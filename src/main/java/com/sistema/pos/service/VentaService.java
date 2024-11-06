package com.sistema.pos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sistema.pos.dto.DetalleVentaDTO;
import com.sistema.pos.dto.VentaDTO;
import com.sistema.pos.entity.Almacen;
import com.sistema.pos.entity.CajaSesion;
import com.sistema.pos.entity.Cliente;
import com.sistema.pos.entity.DetalleVenta;
import com.sistema.pos.entity.Producto;
import com.sistema.pos.entity.ProductoAlmacen;
import com.sistema.pos.entity.Venta;
import com.sistema.pos.repository.CajaSesionRepository;
import com.sistema.pos.repository.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class VentaService {

	@Autowired
	private VentaRepository ventaRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private AlmacenService almacenService;
	
	@Autowired
	private ProductoAlmacenService productoAlmacenService;
	
    @Autowired
    private CajaSesionRepository cajaSesionRepository;

	@Transactional
	public List<Venta> listarVentas() {
		return ventaRepository.findAll();
	}

	public Venta obtenerVenta(Long id) {
		Optional<Venta> venta = ventaRepository.findById(id);
		if (!venta.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la venta" + id);
		}
		return venta.get();
	}

	@Transactional
	public Venta guardarVenta(VentaDTO ventaDTO) {
		
		CajaSesion cajaSesion = cajaSesionRepository.findByCajaIdAndAbiertaTrue(ventaDTO.getId_caja_sesion())
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede realizar la venta, la caja está cerrada."));

		
		Cliente cliente = clienteService.obtenerClientePorId(ventaDTO.getId_cliente());
		Venta venta = new Venta();
		venta.setCliente(cliente);
		venta.setCajaSesion(cajaSesion);
		
		List<DetalleVenta> detalles = new ArrayList<>();
		Double total = 0.00;
		Almacen almacen = almacenService.obtenerAlmacenId(ventaDTO.getId_almacen());
		
		for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalleVentaDTOS()) {
			Producto producto = productoService.obtenerProducto(detalleDTO.getId_producto());
			
			ProductoAlmacen productoAlmacen = productoAlmacenService
		            .obtenerProductoAlmacenPorProductoYAlmacen(almacen.getId(), detalleDTO.getId_producto());
			
			if (productoAlmacen.getStock() < detalleDTO.getCantidad()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"No hay suficiente stock del producto: " + producto.getNombre() + " en el almacén seleccionado.");
			}
			if (producto.getPrecioVenta() != detalleDTO.getPrecio()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"El precio del producto no es válido: " + producto.getNombre());
			}
			
			productoAlmacen.setStock(productoAlmacen.getStock() - detalleDTO.getCantidad());
			productoAlmacenService.actualizarStock(productoAlmacen);
			DetalleVenta detalleVenta = new DetalleVenta();
			detalleVenta.setProducto(producto);
			detalleVenta.setCantidad(detalleDTO.getCantidad());
			detalleVenta.setPrecio(detalleDTO.getPrecio());
			detalleVenta.setMonto(detalleDTO.getPrecio() * detalleDTO.getCantidad());
			detalleVenta.setVenta(venta);
			detalles.add(detalleVenta);
			total += detalleVenta.getMonto();
		}
		venta.setDetalleVentaList(detalles);
		venta.setTotal(total);
		Venta ventaGuardada = ventaRepository.save(venta);
		return ventaGuardada;
	}

}
