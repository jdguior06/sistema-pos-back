package com.sistema.pos.repository;


import com.sistema.pos.entity.Almacen;
import com.sistema.pos.entity.ProductoAlmacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoAlmacenRepository extends JpaRepository<ProductoAlmacen,Long> {
	
    @Query("SELECT COUNT(c) FROM ProductoAlmacen c WHERE c.almacen.id = :idAlmacen AND c.producto.id = :idProducto")
    Long verificarProducto(@Param("idAlmacen") Long idAlmacen, @Param("idProducto") Long idProducto);

    Optional<ProductoAlmacen> findByIdAndAlmacen(Long idProductoAlmacen, Almacen almacen);

    List<ProductoAlmacen> findAllByAlmacen(Almacen almacen);
}
