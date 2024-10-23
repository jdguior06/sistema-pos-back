package com.sistema.pos.service;

import com.sistema.pos.dto.CajaDTO;
import com.sistema.pos.entity.Caja;
import com.sistema.pos.entity.Categoria;
import com.sistema.pos.entity.Cliente;
import com.sistema.pos.entity.Sucursal;
import com.sistema.pos.repository.CajaRepository;
import com.sistema.pos.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;


@Service
public class CajaService {
    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private CajaRepository cajaRepository;


    public List<Caja> findAll() {
        return cajaRepository.findAll();
    }

   /**
    public Optional<Caja> findById(Long id) {
        return cajaRepository.findById(id);
    }
   **/
    public Caja findById(Long id) {
        Optional<Caja> caja = cajaRepository.findById(id);
        if(!caja.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la caja con el id" + id
            );
        }
        return caja.get();
    }

    public Caja store( CajaDTO caja)  {

       Optional<Sucursal> sucursal = sucursalRepository.findById(caja.getId_sucursal());
        Caja nuevo = new Caja();
        nuevo.setNombre(caja.getNombre());
        nuevo.setSucursal(sucursal.get());
        nuevo.setActivo(true);
        Caja creado= cajaRepository.save(nuevo);
        return creado;
    }

    /**
    public void delete(Long id) {

        if (cajaRepository.existsById(id)) {
            cajaRepository.deleteById(id); // Elimina el si existe

        } else {
            throw new IllegalArgumentException("Caja with ID: " + id + " not found.");
        }
    }
  **/

    public void eliminarCaja(Long id) {
       Optional<Caja> caja = cajaRepository.findById(id);
       Caja eliminadacaja=caja.get();
       eliminadacaja.setActivo(false);
       cajaRepository.save(eliminadacaja);
    }

/**
    public Caja update( Long id, CajaDTO cajaActualizado) throws Exception  {

        Optional<Caja> cajaExistente = cajaRepository.findById(id);

        if (cajaExistente.isPresent()) {
            Caja caja = cajaExistente.get();
            Sucursal sucursal = sucursalRepository.findById(cajaActualizado.getId_sucursal())
                    .orElseThrow(() -> new Exception("Sucursal not found"));
            caja.setNombre(cajaActualizado.getNombre());
            caja.setSucursal(sucursal);

            return cajaRepository.save(caja);
        } else {
            throw new IllegalArgumentException("Caja with ID: " + id + " not found.");
        }
    }

**/


     public Caja update( Long id, CajaDTO cajaActualizado){

       Optional<Caja> cajaExistente = cajaRepository.findById(id);
       Caja caja = cajaExistente.get();
       Optional<Sucursal> sucursal = sucursalRepository.findById(cajaActualizado.getId_sucursal());
       caja.setNombre(cajaActualizado.getNombre());
       caja.setSucursal(sucursal.get());
       return cajaRepository.save(caja);

     }

    public List<Caja> buscarCajas(String searchTerm) {
        return cajaRepository.buscarCajas(searchTerm);
    }



}

