package com.sistema.pos.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sistema.pos.dto.CajaSesionDTO;
import com.sistema.pos.entity.CajaSesion;
import com.sistema.pos.entity.Usuario;
import com.sistema.pos.entity.Venta;
import com.sistema.pos.repository.CajaSesionRepository;

@Service
public class CajaSesionService {
	
	@Autowired
	private CajaSesionRepository cajaSesionRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public List<CajaSesion> listarCajasSesiones() {
		return cajaSesionRepository.findAll();
	}
	
	public CajaSesion aperturaDeCaja(CajaSesionDTO cajaSesionDTO) {
		Usuario usuario = usuarioService.obtenerUsuarioAuthenticado();
		CajaSesion cajaSesion = new CajaSesion();
		cajaSesion.setUsuario(usuario);
		cajaSesion.setSaldoInicial(cajaSesionDTO.getMonto());
		cajaSesion.setFechaHoraApertura(LocalDateTime.now());
		cajaSesion.setAbierta(true);
		return cajaSesionRepository.save(cajaSesion);
	}
	
	public CajaSesion obtenerCajaSesion(Long id) {
		CajaSesion cajaSesion = cajaSesionRepository.findById(id)
		        .orElseThrow(() -> new IllegalArgumentException("No se encontró la sesión de caja especificada."));
		    
		    if (!cajaSesion.getAbierta()) {
		        throw new IllegalStateException("La caja ya está cerrada.");
		    }
		return cajaSesion;
	}

	
	public CajaSesion cierreDeCaja(Long cajaSesionId) {
	    CajaSesion cajaSesion = obtenerCajaSesion(cajaSesionId);
	    
	    Double totalVentas = cajaSesion.getVentas().stream()
	        .mapToDouble(Venta::getTotal)
	        .sum();
	    
	    cajaSesion.setSaldoFinal(cajaSesion.getSaldoInicial() + totalVentas);
	    cajaSesion.setFechaHoraCierre(LocalDateTime.now());
	    cajaSesion.setAbierta(false);

	    return cajaSesionRepository.save(cajaSesion);
	}
	
}
