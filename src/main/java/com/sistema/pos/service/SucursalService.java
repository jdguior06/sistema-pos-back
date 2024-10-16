package com.sistema.pos.service;

import com.sistema.pos.dto.SucursalDTO;
import com.sistema.pos.entity.Sucursal;
import com.sistema.pos.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    public Sucursal save(SucursalDTO sucursalDTO) {
        Sucursal sucursal = new Sucursal();
        sucursal.setCodigo(sucursalDTO.getCodigo());
        sucursal.setNit(sucursalDTO.getNit());
        sucursal.setNombre(sucursalDTO.getNombre());
        sucursal.setRazonSocial(sucursalDTO.getRazonSocial());
        sucursal.setDireccion(sucursalDTO.getDireccion());
        sucursal.setActivo(true);
        return sucursalRepository.save(sucursal);
    }

    public Sucursal findById(Long id) {
        Optional<Sucursal> sucursal= sucursalRepository.findById(id);
        if(!sucursal.isPresent()){
            throw new RuntimeException("No se encontro el id de la Sucursal");
        }
        return sucursal.get();
    }

    public Sucursal modificar(Long id, SucursalDTO sucursalDTO)
    {
        Sucursal sucursal = findById(id);
        sucursal.setCodigo(sucursalDTO.getCodigo());
        sucursal.setNit(sucursalDTO.getNit());
        sucursal.setNombre(sucursalDTO.getNombre());
        sucursal.setRazonSocial(sucursalDTO.getRazonSocial());
        sucursal.setDireccion(sucursalDTO.getDireccion());
        return sucursalRepository.save(sucursal);
    }

    public Sucursal eliminar(Long id) {
        Sucursal sucursal = findById(id);
        sucursal.setActivo(false);
        return sucursalRepository.save(sucursal);
    }

    public boolean existsById(Long id) {
        return sucursalRepository.existsById(id);
    }

}