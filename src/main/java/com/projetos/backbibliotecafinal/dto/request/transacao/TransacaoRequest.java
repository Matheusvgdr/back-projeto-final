package com.projetos.backbibliotecafinal.dto.request.transacao;

import com.projetos.backbibliotecafinal.constants.enums.TipoTransacaoEnum;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record TransacaoRequest(
        @NotNull(message = "O campo tipo usuário é obrigatório")
        List<Long> idLivros,
        @NotNull(message = "O campo tipo usuário é obrigatório")
        Long idUsuario,
        @NotNull(message = "O campo tipo usuário é obrigatório")
        TipoTransacaoEnum tipo,
        @NotNull(message = "O campo tipo usuário é obrigatório")
        LocalDate dataDevolucao
) {
}
