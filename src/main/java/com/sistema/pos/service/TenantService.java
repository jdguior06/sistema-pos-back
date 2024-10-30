package com.sistema.pos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.pos.entity.PlanType;
import com.sistema.pos.entity.Tenant;
import com.sistema.pos.entity.Usuario;
import com.sistema.pos.repository.TenantRepository;

@Service
public class TenantService {
	
	@Autowired
	private TenantRepository tenantRepository; 
	
	public Tenant crearNuevoTenantConPlan(String nombre, PlanType plan, Usuario administradorPrincipal) {
	    Tenant tenant = new Tenant();
	    tenant.setNombre(nombre);
	    tenant.setPlan(plan);

	    switch (plan) {
	        case BASICO:
	            tenant.setMaxUsuarios(5);
	            tenant.setMaxSucursales(1);
	            break;
	        case INTERMEDIO:
	            tenant.setMaxUsuarios(10);
	            tenant.setMaxSucursales(3);
	            break;
	        case PREMIUM:
	            tenant.setMaxUsuarios(20);
	            tenant.setMaxSucursales(5);
	            break;
	    }

//	    administradorPrincipal.setEsAdministradorPrincipal(true);
//	    administradorPrincipal.setTenant(tenant);
	    tenant.getUsuarios().add(administradorPrincipal);

	    tenantRepository.save(tenant);
	    return tenant;
	}

}
