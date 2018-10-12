package com.api.autonomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.autonomo.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

}
