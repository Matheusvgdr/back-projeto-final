package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel, Long> {

    Optional<LivroModel> findByIsbn(String isbn);
}
