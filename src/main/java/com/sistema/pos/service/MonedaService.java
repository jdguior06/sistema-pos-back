package com.sistema.pos.service;

import com.sistema.pos.entity.Moneda;
import com.sistema.pos.repository.MonedaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MonedaService {
 @Autowired
    private MonedaRepository monedaRepository;

   public List<Moneda> findAll() {return monedaRepository.findAll();}

    public Moneda findById(Long idMOneda){
        Optional<Moneda> moneda = monedaRepository.findById(idMOneda);
        if(!moneda.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la moneda" + idMOneda);
        }
        return moneda.get();
    }

    public Moneda save(Moneda moneda){
       Moneda newmoneda= new Moneda();
       newmoneda.setMoneda(moneda.getMoneda());
       newmoneda.setCambio(moneda.getCambio());
       return monedaRepository.save(newmoneda);
    }

    public Moneda update(Moneda moneda,Long id){
       Moneda newmoneda= findById(id);
       newmoneda.setMoneda(moneda.getMoneda());
       newmoneda.setCambio(moneda.getCambio());
       return monedaRepository.save(newmoneda);
    }

    public Moneda delete(Long id){
       Moneda newmoneda= findById(id);
       newmoneda.setActivo(false);
       return monedaRepository.save(newmoneda);
    }

}

