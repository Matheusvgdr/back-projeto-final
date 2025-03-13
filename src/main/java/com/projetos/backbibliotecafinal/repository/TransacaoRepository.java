package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.TransacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoModel, Long> {

    //TODO: verificar retorno do metodo
    @Query("SELECT T FROM TransacaoModel T WHERE T.usuario.id = ?1 AND T.status = com.projetos.backbibliotecafinal.constants.enums.StatusEmprestimoEnum.EMPRESTADO")
    Optional<TransacaoModel> findByIdUsuario(Long idUsuario);

}
