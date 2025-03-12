package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.AutorModel;
import com.projetos.backbibliotecafinal.entity.EditoraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EditoraRepository extends JpaRepository<EditoraModel, Long> {

    Optional<EditoraModel> findByNome(String nome);

    @Query("SELECT Ed FROM EditoraModel Ed WHERE Ed.dataExclusao IS NULl ")
    List<EditoraModel> findAllActive();

    @Query("SELECT Ed FROM EditoraModel Ed WHERE Ed.id = ?1 AND Ed.dataExclusao IS NULl ")
    Optional<EditoraModel> findByIdActive(Long idEditora);
}
