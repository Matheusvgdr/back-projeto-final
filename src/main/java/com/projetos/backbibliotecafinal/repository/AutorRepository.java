package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.AutorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<AutorModel, Long> {
    Optional<AutorModel> findByNome(String nome);
}
