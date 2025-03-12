package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel, Long> {

    @Query("SELECT L FROM LivroModel L WHERE L.dataExclusao IS NULL AND L.isbn = ?1")
    Optional<LivroModel> findByIsbn(String isbn);

    @Query("SELECT L FROM LivroModel L WHERE L.dataExclusao IS NULL AND L.id = ?1")
    Optional<LivroModel> findByIdActive(Long id);

    @Query("SELECT L FROM LivroModel L WHERE L.dataExclusao IS NULL")
    List<LivroModel> findAllActive();
}
