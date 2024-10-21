package com.sistema.pos.service;

import com.sistema.pos.dto.DetalleNotaDTO;
import com.sistema.pos.entity.Almacen;
import com.sistema.pos.entity.Producto;
import com.sistema.pos.entity.ProductoAlmacen;
import com.sistema.pos.entity.Sucursal;
import com.sistema.pos.repository.ProductoAlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoAlmacenService {
    @Autowired
    ProductoAlmacenRepository productoAlmacenRepository;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private AlmacenService almacenService;
    @Autowired
    private ProductoService productoService;

    public List<ProductoAlmacen> findAll() {
        return productoAlmacenRepository.findAll();
    }


    public boolean existe(Long id_almacen, Long id_producto){
        Long cantidad = productoAlmacenRepository.verificarProducto(id_almacen,id_producto);
        return cantidad != null && cantidad > 0; // Devuelve true si hay al menos un producto
    }

    public ProductoAlmacen obtener(Long id_almacen){
        Optional<ProductoAlmacen> productoA = productoAlmacenRepository.findById(id_almacen);
        if(!productoA.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el producto con el id" + id_almacen);

        }return productoA.get();
    }


    // Verifica si el producto ya está en el almacén y actualiza el stock
    public ProductoAlmacen save(ProductoAlmacen productoAlmacen, DetalleNotaDTO detalleNotaDTO) {
        // Obtener el almacén y el producto
        Almacen almacen = almacenService.obtenerAlmacenId(productoAlmacen.getAlmacen().getId());
        Producto producto = productoService.obtenerProducto(productoAlmacen.getProducto().getId());

        // Verificar si el producto ya está en el almacén
        Optional<ProductoAlmacen> existente = productoAlmacenRepository.findByAlmacen_IdAndProducto_Id(
                productoAlmacen.getAlmacen().getId(),
                productoAlmacen.getProducto().getId()
        );

        // Si ya existe el producto en ese almacén, sumamos la cantidad al stock actual
        if (existente.isPresent()) {
            ProductoAlmacen productoExistente = existente.get();
            int nuevoStock = productoExistente.getStock() + detalleNotaDTO.getCantidad();
            productoExistente.setStock(nuevoStock); // Actualiza el stock sumando la nueva cantidad
            return productoAlmacenRepository.save(productoExistente);
        } else {
            // Si no existe, creamos un nuevo registro con la cantidad del DetalleNotaDTO
            productoAlmacen.setStock(detalleNotaDTO.getCantidad());
            productoAlmacen.setAlmacen(almacen);
            productoAlmacen.setProducto(producto);
            productoAlmacen.setActivo(true);
            return productoAlmacenRepository.save(productoAlmacen);
        }
    }
    public ProductoAlmacen actualizar(Long id, ProductoAlmacen productoAlmacen, DetalleNotaDTO detalleNotaDTO) {
        Almacen almacen = almacenService.obtenerAlmacenId(productoAlmacen.getAlmacen().getId());
        Producto producto = productoService.obtenerProducto(productoAlmacen.getProducto().getId());

        // Verificar si el producto ya está en el almacén
        Optional<ProductoAlmacen> existente = productoAlmacenRepository.findByAlmacen_IdAndProducto_Id(
                productoAlmacen.getAlmacen().getId(),
                productoAlmacen.getProducto().getId()
        );

        if (existente.isPresent()) {
            ProductoAlmacen productoExistente = existente.get();
            int nuevoStock = productoExistente.getStock() + detalleNotaDTO.getCantidad();
            productoExistente.setStock(nuevoStock); // Actualiza el stock sumando la nueva cantidad
            return productoAlmacenRepository.save(productoExistente);
        } else {
            productoAlmacen.setStock(detalleNotaDTO.getCantidad());
            productoAlmacen.setAlmacen(almacen);
            productoAlmacen.setProducto(producto);
            return productoAlmacenRepository.save(productoAlmacen);
        }
    }

    public ProductoAlmacen eliminar(Long id) {
        ProductoAlmacen productoAlmacen =obtener(id);
        productoAlmacen.setActivo(false);
        return productoAlmacenRepository.save(productoAlmacen);
    }
}
