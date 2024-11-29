package com.sistema.pos.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Tenant {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String tenantId;

    @Enumerated(EnumType.STRING)
    private PlanType plan;

    private int maxUsuarios;
    private int maxSucursales;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private Set<Usuario> usuarios = new HashSet<>();

}
