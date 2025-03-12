package com.projetos.backbibliotecafinal.repository;

import com.projetos.backbibliotecafinal.entity.EmprestimoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {

    //TODO: verificar retorno do metodo
    @Query("SELECT Em FROM EmprestimoModel Em WHERE Em.usuario.id = ?1 AND Em.status = com.projetos.backbibliotecafinal.constants.enums.StatusEmprestimoEnum.EMPRESTADO")
    Optional<EmprestimoModel> findByIdUsuario(Long idUsuario);
}
