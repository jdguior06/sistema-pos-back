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

    //Optional<ProductoAlmacen> findByIdAndAlmacen(Long idProductoAlmacen, Almacen almacen);

    //List<ProductoAlmacen> findAllByAlmacen(Almacen almacen);
    Optional<ProductoAlmacen> findByAlmacen_IdAndProducto_Id(Long almacenId, Long productoId);

    List<ProductoAlmacen> findByAlmacen_Id(Long almacenId);


    @Query("SELECT pa FROM ProductoAlmacen pa WHERE " +
            "(:almacen IS NULL OR pa.almacen.id = :almacen) AND " +
            "(:producto IS NULL OR pa.producto.id = :producto) AND " +
            "(:stockMin IS NULL OR pa.stock >= :stockMin) AND " +
            "(:stockMax IS NULL OR pa.stock <= :stockMax)")
    List<ProductoAlmacen> findByAlmacenAndProductoAndStockRange(
            @Param("almacen") Long almacen,
            @Param("producto") Long producto,
            @Param("stockMin") Integer stockMin,
            @Param("stockMax") Integer stockMax
    );


}
