package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.TransacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoModel, Long> {

    @Query("SELECT T FROM TransacaoModel T WHERE T.usuario.id = ?1 AND T.status = com.projetos.backbibliotecafinal.constants.enums.StatusEmprestimoEnum.EMPRESTADO")
    Optional<TransacaoModel> findEmprestimoByIdUsuario(Long idUsuario);

    @Query("SELECT T FROM TransacaoModel T WHERE T.usuario.id = ?1")
    List<TransacaoModel> findTransacoesByIdUsuario(Long idUsuario);

}
