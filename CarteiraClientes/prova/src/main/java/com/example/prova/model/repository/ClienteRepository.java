package com.example.prova.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.prova.model.entity.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>{
    
    @Query(value ="SELECT cliente.cnpj FROM cliente  where cliente.cnpj = ?1 "
    , nativeQuery = true) 
    String findByCnpj(String cnpj);
}
