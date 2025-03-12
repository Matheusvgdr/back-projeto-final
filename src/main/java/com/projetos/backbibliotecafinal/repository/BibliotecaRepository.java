package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.BibliotecaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BibliotecaRepository extends JpaRepository<BibliotecaModel, Long> {
}
