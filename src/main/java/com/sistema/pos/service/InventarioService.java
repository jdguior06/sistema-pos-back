package com.sistema.pos.service;

import com.sistema.pos.entity.Inventario;
import com.sistema.pos.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> listInventarios() {
        return inventarioRepository.findAll();
    }

    public Inventario saveInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }


}
