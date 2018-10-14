package com.api.autonomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.autonomo.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
