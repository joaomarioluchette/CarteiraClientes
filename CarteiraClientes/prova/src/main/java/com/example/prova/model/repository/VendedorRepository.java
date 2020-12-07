package com.example.prova.model.repository;

import com.example.prova.model.entity.Vendedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface VendedorRepository extends JpaRepository<Vendedor,Long>{
    @Query(value ="SELECT vendedor.cpf FROM vendedor  where vendedor.cpf = ?1"
    , nativeQuery = true) 
    String findByCpf(String cpf);
}
