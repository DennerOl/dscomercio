package com.devsuperior.dscomercio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscomercio.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByAuthority(String authority);

}
