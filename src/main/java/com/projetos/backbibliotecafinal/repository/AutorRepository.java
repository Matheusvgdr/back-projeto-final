package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.AutorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<AutorModel, Long> {
    Optional<AutorModel> findByNome(String nome);

    @Query("SELECT A FROM AutorModel A WHERE A.dataExclusao is null ")
    List<AutorModel> findAllActive();
}
