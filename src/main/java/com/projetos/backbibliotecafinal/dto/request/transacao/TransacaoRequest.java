package com.projetos.backbibliotecafinal.dto.request.transacao;

import com.projetos.backbibliotecafinal.constants.enums.TipoTransacaoEnum;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record TransacaoRequest(
        @NotNull
        List<Long> idLivros,
        @NotNull
        Long idUsuario,
        @NotNull(message = "O campo tipo de transação é obrigatório")
        TipoTransacaoEnum tipo,
        @NotNull(message = "O campo data de devolução é obrigatório")
        LocalDate dataDevolucao
) {
}
