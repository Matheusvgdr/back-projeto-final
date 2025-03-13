package com.projetos.backbibliotecafinal.dto.response;

import com.projetos.backbibliotecafinal.constants.enums.StatusEmprestimoEnum;
import com.projetos.backbibliotecafinal.constants.enums.TipoTransacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoResponse {
    private BigDecimal valor;
    private LocalDate dataTransacao;
    private LocalDate dataDevolucao;
    private StatusEmprestimoEnum status;
    private TipoTransacaoEnum tipo;
    private List<LivroResponse> livros;
}
