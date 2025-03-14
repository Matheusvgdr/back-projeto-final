package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByEmail(String email);

    @Query("SELECT U FROM UsuarioModel U WHERE U.id = ?1 AND U.dataExclusao IS NULL AND U.tipo = com.projetos.backbibliotecafinal.constants.enums.TipoUsuarioEnum.FUNCIONARIO")
    Optional<UsuarioModel> findFuncionarioActive(Long id);
}
