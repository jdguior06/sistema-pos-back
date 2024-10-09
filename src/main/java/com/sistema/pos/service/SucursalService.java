package com.sistema.pos.service;

import com.sistema.pos.entity.Sucursal;
import com.sistema.pos.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> listSucursales() {
        return sucursalRepository.findAll();
    }

    public Sucursal obtenerSucursalPorId(Long id) {
        return sucursalRepository.findById(id).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
    }

    public Sucursal saveSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public void deleteSucursal(Long id) {
        sucursalRepository.deleteById(id);
    }
}
