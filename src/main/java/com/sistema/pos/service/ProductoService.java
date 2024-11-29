package com.sistema.pos.service;

import com.sistema.pos.config.LoggableAction;
import com.sistema.pos.dto.ProductoDTO;
import com.sistema.pos.entity.Categoria;
import com.sistema.pos.entity.Producto;
import com.sistema.pos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CategoriaService categoriaService;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto obtenerProducto(Long id) {
		Optional<Producto> producto = productoRepository.findById(id);
		if (!producto.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el producto con el id" + id);
		}
		return producto.get();
	}

    @LoggableAction
    public Producto save(ProductoDTO productoDTO) {
    	Categoria categoria = categoriaService.findById(productoDTO.getId_categoria());
    	Producto producto = new Producto();
    	producto.setNombre(productoDTO.getNombre());
    	producto.setCodigo(productoDTO.getCodigo());
    	producto.setDescripcion(productoDTO.getDescripcion());
    	producto.setPrecioCompra(productoDTO.getPrecioCompra());
    	producto.setPrecioVenta(productoDTO.getPrecioVenta());
    	producto.setFoto(productoDTO.getFoto());
    	producto.setCategoria(categoria);
    	producto.setActivo(true);
        
        return productoRepository.save(producto);
    }
    
    @LoggableAction
    public Producto actualizarProducto(Long id, ProductoDTO productoDTO) {
    	Producto producto = obtenerProducto(id);
    	Categoria categoria = categoriaService.findById(productoDTO.getId_categoria());
    	producto.setNombre(productoDTO.getNombre());
    	producto.setCodigo(productoDTO.getCodigo());
    	producto.setDescripcion(productoDTO.getDescripcion());
    	producto.setPrecioCompra(productoDTO.getPrecioCompra());
    	producto.setPrecioVenta(productoDTO.getPrecioVenta());
    	producto.setFoto(productoDTO.getFoto());
    	producto.setCategoria(categoria);
        
    	return productoRepository.save(producto);
    }
    
    @LoggableAction
    public Producto eliminarProducto(Long id) {
		Producto producto = obtenerProducto(id);
		producto.setActivo(false);
		return productoRepository.save(producto);
	}
    
}
