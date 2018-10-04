package com.api.autonomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.autonomo.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long>{

}
