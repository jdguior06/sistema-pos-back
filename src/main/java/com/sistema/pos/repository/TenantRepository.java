package com.sistema.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pos.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

}
