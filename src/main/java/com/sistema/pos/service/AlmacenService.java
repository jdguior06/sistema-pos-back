package com.sistema.pos.service;

import com.sistema.pos.dto.AlmacenDTO;
import com.sistema.pos.entity.Almacen;
import com.sistema.pos.entity.Proveedor;
import com.sistema.pos.entity.Sucursal;
import com.sistema.pos.repository.AlmacenRepository;
import com.sistema.pos.repository.ProveedorRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlmacenService {
    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private ProveedorRespository proveedorRespository;

    public List<Almacen> findAll() {
        return almacenRepository.findAll();
    }

    public Almacen obtenerAlmacenId(Long idAlmacen) {
        Optional<Almacen> almacen = almacenRepository.findById(idAlmacen);
        if(!almacen.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"no existe almacen " + idAlmacen);
        }
        return almacen.get();
    }
    public Almacen obtenerAlmacenPorProv(Long proveedorId) {
        Proveedor proveedor = proveedorRespository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));

        return almacenRepository.findById(proveedor.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado para el proveedor con ID: " + proveedorId));

    }

    public Almacen save(AlmacenDTO almacen) {
        Sucursal sucursal= sucursalService.findById(almacen.getId_sucursal());
     Almacen almacen1 = new Almacen();

     almacen1.setDescripcion(almacen.getDescripcion());
     almacen1.setActivo(true);
     almacen1.setSucursal(sucursal);
     return almacenRepository.save(almacen1);
    }

    public Almacen ModificarAlmacen(Long id,AlmacenDTO almacen) {
        Almacen almacen1= obtenerAlmacenId(id);

        almacen1.setDescripcion(almacen.getDescripcion());
        almacen1.setSucursal(sucursalService.findById(almacen.getId_sucursal()));
        return almacenRepository.save(almacen1);
    }


    public Almacen eliminar(Long id) {
        Almacen almacen = obtenerAlmacenId(id);
        almacen.setActivo(false);
        return almacenRepository.save(almacen);
    }

    public boolean existsById(Long id) {
        return almacenRepository.existsById(id);
    }


}
