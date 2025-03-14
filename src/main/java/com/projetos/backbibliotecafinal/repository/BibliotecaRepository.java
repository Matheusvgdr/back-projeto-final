package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.AutorModel;
import com.projetos.backbibliotecafinal.entity.BibliotecaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface BibliotecaRepository extends JpaRepository<BibliotecaModel, Long> {

    @Query("SELECT B FROM BibliotecaModel B WHERE B.id = ?1 AND B.dataExclusao is null ")
    Optional<BibliotecaModel> findByIdActive(Long id);

    @Query("SELECT B FROM BibliotecaModel B WHERE B.usuario.id = ?1 AND B.usuario.dataExclusao IS NULL AND B.dataExclusao IS NULL ")
    Optional<BibliotecaModel> findByUsuario(Long idUsuario);
    @Query("SELECT B FROM BibliotecaModel B WHERE B.dataExclusao is null ")
    List<BibliotecaModel> findAllActive();
}
