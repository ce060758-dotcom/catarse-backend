package com.catarse.backend.repository;

import com.catarse.backend.model.Projeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjetoRepository extends MongoRepository<Projeto, String> {

    Optional<Projeto> findByTitulo(String titulo);

    Page<Projeto> findByCategoria(String categoria, Pageable pageable);

    Page<Projeto> findByCriadorId(String criadorId, Pageable pageable);

    Page<Projeto> findByStatus(Projeto.StatusProjeto status, Pageable pageable);
}