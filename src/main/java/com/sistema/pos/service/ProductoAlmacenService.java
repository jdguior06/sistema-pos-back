package com.sistema.pos.service;

import com.sistema.pos.entity.ProductoAlmacen;
import com.sistema.pos.repository.ProductoAlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoAlmacenService {
    @Autowired
    ProductoAlmacenRepository productoAlmacenRepository;

    public boolean existe(Long id_almacen, Long id_producto){
        Long cantidad = productoAlmacenRepository.verificarProducto(id_almacen,id_producto);
        return cantidad != null && cantidad > 0; // Devuelve true si hay al menos un producto
    }

    public Optional<ProductoAlmacen> obtenerProducto(Long idAlmacen, Long id_producto) {
        return productoAlmacenRepository.traerProducto(idAlmacen,id_producto);
    }



    public ProductoAlmacen save(ProductoAlmacen productoAlmacen) {
        return productoAlmacenRepository.save(productoAlmacen);
    }


    public List<ProductoAlmacen> findAll() {
        return productoAlmacenRepository.findAll();
    }
}
