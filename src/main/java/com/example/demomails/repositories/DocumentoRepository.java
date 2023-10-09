package com.example.demomails.repositories;

import com.example.demomails.entities.Documento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

}
