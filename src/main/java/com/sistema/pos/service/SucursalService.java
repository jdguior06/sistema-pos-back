package com.sistema.pos.service;

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

    public Optional<Sucursal> findById(Long id) {
        return sucursalRepository.findById(id);
    }

    public Sucursal save(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public boolean existsById(Long id) {
        return sucursalRepository.existsById(id);
    }

    public void deleteById(Long id) {
        sucursalRepository.deleteById(id);
    }
}