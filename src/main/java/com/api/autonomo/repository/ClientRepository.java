package com.api.autonomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.autonomo.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
