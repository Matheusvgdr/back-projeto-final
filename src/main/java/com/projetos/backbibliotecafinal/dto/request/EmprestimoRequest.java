package com.projetos.backbibliotecafinal.dto.request;

import com.projetos.backbibliotecafinal.constants.enums.StatusEmprestimoEnum;
import java.time.LocalDate;
import java.util.List;

public record EmprestimoRequest(
        List<Long> idLivros,
        Long idUsuario,
        StatusEmprestimoEnum status,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucao

) {
}
