package com.api.autonomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.autonomo.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
