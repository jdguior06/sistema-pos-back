package com.sistema.pos.service;

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

//    public boolean existe(Long id_almacen, Long id_producto){
//        Long cantidad = productoAlmacenRepository.verificarProducto(id_almacen,id_producto);
//        if (cantidad>0){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }


//    public Optional<ProductoAlmacen> traerProducto(Long id_almacen, Long id_producto ){
//        return productoAlmacenRepository.traerProducto(id_almacen,id_producto);
//    }

//    public List<ProductoAlmacen> findAll() {
//        return productoAlmacenRepository.findAll();
//    }
//
//
//    public boolean existe(Long id_almacen, Long id_producto){
//        Long cantidad = productoAlmacenRepository.verificarProducto(id_almacen,id_producto);
//        return cantidad != null && cantidad > 0; // Devuelve true si hay al menos un producto
//    }
//
//    public ProductoAlmacen obtener(Long id_almacen){
//        Optional<ProductoAlmacen> productoA = productoAlmacenRepository.findById(id_almacen);
//        if(!productoA.isPresent()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el producto con el id" + id_almacen);
//
//        }return productoA.get();
//    }
//
//
//
//    public ProductoAlmacen save(ProductoAlmacen productoAlmacen) {
//        Almacen almacen = almacenService.obtenerAlmacenId(productoAlmacen.getId_almacen().getId());
//        Producto producto1=productoService.obtenerProducto(productoAlmacen.getId_producto().getId());
//        ProductoAlmacen productoAlmacen1=new ProductoAlmacen();
//        productoAlmacen1.setCantidad(productoAlmacen.getCantidad());
//        productoAlmacen1.setPrecio_compra(productoAlmacen.getPrecio_compra());
//        productoAlmacen1.setGanancia(productoAlmacen.getGanancia());
//        productoAlmacen1.setPrecio_venta(productoAlmacen.getPrecio_venta());
//        productoAlmacen1.setId_almacen(almacen);
//        productoAlmacen1.setId_producto(producto1);
//        productoAlmacen1.setActivo(true);
//        return productoAlmacenRepository.save(productoAlmacen);
//    }
//
//    public ProductoAlmacen actualizar(Long id, ProductoAlmacen productoAlmacen) {
//        Almacen almacen = almacenService.obtenerAlmacenId(productoAlmacen.getId_almacen().getId());
//        Producto producto1=productoService.obtenerProducto(productoAlmacen.getId_producto().getId());
//        ProductoAlmacen productoAlmacen1=new ProductoAlmacen();
//        productoAlmacen1.setCantidad(productoAlmacen.getCantidad());
//        productoAlmacen1.setPrecio_compra(productoAlmacen.getPrecio_compra());
//        productoAlmacen1.setGanancia(productoAlmacen.getGanancia());
//        productoAlmacen1.setPrecio_venta(productoAlmacen.getPrecio_venta());
//        productoAlmacen1.setId_almacen(almacen);
//        productoAlmacen1.setId_producto(producto1);
//        return productoAlmacenRepository.save(productoAlmacen);
//    }
//
//    public ProductoAlmacen eliminar(Long id) {
//        ProductoAlmacen productoAlmacen =obtener(id);
//        productoAlmacen.setActivo(false);
//        return productoAlmacenRepository.save(productoAlmacen);
//    }
}
