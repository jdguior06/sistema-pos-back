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

	public List<Almacen> findAllBySucursalId(Long idSucursal) {
        Sucursal sucursal = sucursalService.findById(idSucursal);
        return almacenRepository.findAllBySucursal(sucursal);
    }

	public Almacen obtenerAlmacenId(Long idAlmacen) {
		Optional<Almacen> almacen = almacenRepository.findById(idAlmacen);
		if (!almacen.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no existe almacen" + idAlmacen);
		}
		return almacen.get();
	}

	public Almacen obtenerAlmacenPorProv(Long proveedorId) {
        Proveedor proveedor = proveedorRespository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));

        return almacenRepository.findById(proveedor.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado para el proveedor con ID: " + proveedorId));

    }

    public Almacen obtenerAlmacenDeSucursal(Long idSucursal, Long idAlmacen) {
        Sucursal sucursal = sucursalService.findById(idSucursal);
        Optional<Almacen> almacen = almacenRepository.findByIdAndSucursal(idAlmacen, sucursal);
        if (!almacen.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el almacén con ID " + idAlmacen + " en la sucursal " + idSucursal);
        }
        return almacen.get();
    }

	public Almacen saveInSucursal(Long idSucursal, AlmacenDTO almacenDTO) {
        Sucursal sucursal = sucursalService.findById(idSucursal);
        Almacen almacen = new Almacen();
        almacen.setNumero(almacenDTO.getNumero());
        almacen.setDescripcion(almacenDTO.getDescripcion());
        almacen.setActivo(true);
        almacen.setSucursal(sucursal);
        return almacenRepository.save(almacen);
    }

	public Almacen modificarAlmacenEnSucursal(Long idSucursal, Long idAlmacen, AlmacenDTO almacenDTO) {
        Sucursal sucursal = sucursalService.findById(idSucursal);
        Almacen almacen = obtenerAlmacenDeSucursal(idSucursal, idAlmacen);
        almacen.setNumero(almacenDTO.getNumero());
        almacen.setDescripcion(almacenDTO.getDescripcion());
        almacen.setSucursal(sucursal);
        return almacenRepository.save(almacen);
    }

	public void desactivarAlmacenEnSucursal(Long idSucursal, Long idAlmacen) {
        Almacen almacen = obtenerAlmacenDeSucursal(idSucursal, idAlmacen);
        almacen.setActivo(false);
        almacenRepository.save(almacen);
    }

	public boolean existsById(Long id) {
		return almacenRepository.existsById(id);
	}


}
