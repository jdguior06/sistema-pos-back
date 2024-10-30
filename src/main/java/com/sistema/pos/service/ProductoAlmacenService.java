package com.sistema.pos.service;

import com.sistema.pos.dto.DetalleNotaDTO;
import com.sistema.pos.dto.ProductoAlmacenDTO;
import com.sistema.pos.entity.Almacen;
import com.sistema.pos.entity.Producto;
import com.sistema.pos.entity.ProductoAlmacen;
import com.sistema.pos.entity.Sucursal;
import com.sistema.pos.repository.ProductoAlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<ProductoAlmacenDTO> findAllByAlmacenId(Long idAlmacen) {
        List<ProductoAlmacenDTO> productosAlmacenDTOList = new ArrayList<>();

        // Obtener productos almacenados en el almacén dado
        List<ProductoAlmacen> productosAlmacen = productoAlmacenRepository.findByAlmacen_Id(idAlmacen);

        for (ProductoAlmacen productoAlmacen : productosAlmacen) {
            Producto producto = productoAlmacen.getProducto();

            // Crear un nuevo DTO combinando los datos de Producto y ProductoAlmacen
            ProductoAlmacenDTO productoAlmacenDTO = new ProductoAlmacenDTO(
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getId(),
                    productoAlmacen.getStock()
            );

            productosAlmacenDTOList.add(productoAlmacenDTO);
        }

        return productosAlmacenDTOList;
    }


    public boolean existe(Long id_almacen, Long id_producto){
        Long cantidad = productoAlmacenRepository.verificarProducto(id_almacen,id_producto);
        return cantidad != null && cantidad > 0; // Devuelve true si hay al menos un producto
    }

    public ProductoAlmacen obtener(Long id){
        Optional<ProductoAlmacen> productoA = productoAlmacenRepository.findById(id);
        if(!productoA.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el producto con el id" + id);

        }return productoA.get();
    }
    /*public ProductoAlmacen obtenerProductoDelAlmacen(Long idAlmacen, Long idProductoAlmacen){
    	Almacen almacen = almacenService.obtenerAlmacenId(idAlmacen);
        Optional<ProductoAlmacen> productoAlmacen = productoAlmacenRepository.findByIdAndAlmacen(idAlmacen, almacen);
        if(!productoAlmacen.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el producto con el id" + idProductoAlmacen);

        }return productoAlmacen.get();
    }*/


    
    public ProductoAlmacen save(ProductoAlmacen productoAlmacen, DetalleNotaDTO detalleNotaDTO) {
        // Obtener el almacén y el producto
        Almacen almacen = almacenService.obtenerAlmacenId(productoAlmacen.getAlmacen().getId());
        Producto producto = productoService.obtenerProducto(productoAlmacen.getProducto().getId());


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



    public ProductoAlmacen eliminar(Long id) {
        ProductoAlmacen productoAlmacen =obtener(id);
        productoAlmacen.setActivo(false);
        return productoAlmacenRepository.save(productoAlmacen);
    }
}
