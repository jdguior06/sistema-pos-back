package com.sistema.pos.repository;

import com.sistema.pos.entity.Almacen;
import com.sistema.pos.entity.Sucursal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
    List<Almacen> findByProveedores_Id(Long proveedorId);
	
	List<Almacen> findAllBySucursal(Sucursal sucursal);
	
    Optional<Almacen> findByIdAndSucursal(Long idAlmacen, Sucursal sucursal);

}
