package com.api.autonomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.autonomo.model.Depot;

@Repository
public interface DepotRepository extends JpaRepository<Depot, Long>{

}
