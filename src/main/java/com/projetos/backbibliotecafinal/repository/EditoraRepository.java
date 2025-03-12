package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.EditoraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EditoraRepository extends JpaRepository<EditoraModel, Long> {
    Optional<EditoraModel> findByNome(String nome);
}
