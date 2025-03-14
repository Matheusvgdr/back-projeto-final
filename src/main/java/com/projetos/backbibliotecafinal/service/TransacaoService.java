package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.constants.enums.StatusEmprestimoEnum;
import com.projetos.backbibliotecafinal.constants.enums.TipoTransacaoEnum;
import com.projetos.backbibliotecafinal.constants.messages.TransacaoMessage;
import com.projetos.backbibliotecafinal.dto.request.transacao.TransacaoRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.TransacaoResponse;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import com.projetos.backbibliotecafinal.entity.TransacaoModel;
import com.projetos.backbibliotecafinal.handler.exceptions.TransacaoNaoEncontradaException;
import com.projetos.backbibliotecafinal.handler.exceptions.TransacaoNaoPermitidaException;
import com.projetos.backbibliotecafinal.repository.TransacaoRepository;
import com.projetos.backbibliotecafinal.utils.mapper.LivroMapper;
import com.projetos.backbibliotecafinal.utils.mapper.TransacaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final TransacaoMapper transacaoMapper;
    private final UsuarioService usuarioService;
    private final LivroService livroService;
    private final LivroMapper livroMapper;

    public ApiResponse<?> salvar(TransacaoRequest transacaoRequest) {

        validarDatas(LocalDate.now(), transacaoRequest.dataDevolucao());

        if (transacaoRequest.tipo() == TipoTransacaoEnum.EMPRESTIMO) {
            transacaoRepository.findEmprestimoByIdUsuario(transacaoRequest.idUsuario()).ifPresentOrElse(transacaoModel -> {

                throw new TransacaoNaoPermitidaException(TransacaoMessage.EMPRESTIMO_LOCALIZADO);
            }, () -> {
                transacaoRepository.save(montarTransacao(transacaoRequest));
            });
        } else {
            transacaoRepository.save(montarTransacao(transacaoRequest));
        }


        return new ApiResponse<>(null, TransacaoMessage.SAVE_SUCCESS, HttpStatus.CREATED.value());
    }

    public ApiResponse<TransacaoResponse> buscarEmprestimosPorUsuario(long idUsuario) {
        var transacao = transacaoRepository.findEmprestimoByIdUsuario(idUsuario);

        if (transacao.isEmpty())
            throw new TransacaoNaoPermitidaException(TransacaoMessage.REGISTER_NOT_FOUND);

        var response = new TransacaoResponse();

        response.setValor(transacao.get().getValor());
        response.setDataTransacao(transacao.get().getDataTransacao());
        response.setDataDevolucao(transacao.get().getDataDevolucao());
        response.setTipo(transacao.get().getTipo());
        response.setStatus(transacao.get().getStatus());
        response.setLivros(livroMapper.toListLivroResponse(transacao.get().getLivros()));

        return new ApiResponse<>(response, TransacaoMessage.SEARCH_LIST_SUCCESS, HttpStatus.OK.value());
    }

    public ApiResponse<List<TransacaoResponse>> buscarTransacoes(long idUsuario) {
        var transacao = transacaoRepository.findTransacoesByIdUsuario(idUsuario);

        if (transacao.isEmpty())
            throw new TransacaoNaoPermitidaException("transacao");

        var listaTransacoes = new ArrayList<TransacaoResponse>();

        transacao.forEach(transacaoModel -> {
            var response = new TransacaoResponse();
            response.setValor(transacaoModel.getValor());
            response.setDataTransacao(transacaoModel.getDataTransacao());
            response.setDataDevolucao(transacaoModel.getDataDevolucao());
            response.setTipo(transacaoModel.getTipo());
            response.setStatus(transacaoModel.getStatus());
            response.setLivros(livroMapper.toListLivroResponse(transacaoModel.getLivros()));

            listaTransacoes.add(response);
        });


        return new ApiResponse<>(listaTransacoes, TransacaoMessage.SEARCH_LIST_SUCCESS, HttpStatus.OK.value());
    }

    public ApiResponse<?> devolverLivro(Long idUsuario) {
        var transacao = transacaoRepository.findEmprestimoByIdUsuario(idUsuario);

        if (transacao.isEmpty())
            throw new TransacaoNaoEncontradaException(TransacaoMessage.REGISTER_NOT_FOUND);

        var emprestimo = transacao.get();

        emprestimo.getLivros().forEach(livro -> {
            livro.setDataExclusao(null);
            livroService.salvarLivroEmprestimo(livro);
        });

        if (emprestimo.getDataDevolucao().isBefore(LocalDate.now())) {

            var diasAtraso = Period.between(emprestimo.getDataDevolucao(), LocalDate.now()).getDays();
            emprestimo.setMulta(emprestimo.getValor().multiply(BigDecimal.valueOf(0.10 * diasAtraso)));
            emprestimo.setStatus(StatusEmprestimoEnum.ENTREGUE);

            transacaoRepository.save(emprestimo);

            return new ApiResponse<>(null, TransacaoMessage.MULTA_EMPRESTIMO.formatted(emprestimo.getMulta(), diasAtraso), HttpStatus.OK.value());
        } else {

            emprestimo.setDataDevolucao(LocalDate.now());
            emprestimo.setStatus(StatusEmprestimoEnum.ENTREGUE);

            transacaoRepository.save(emprestimo);
            return new ApiResponse<>(null, TransacaoMessage.DEVOLUCAO_EMPRESTIMO, HttpStatus.OK.value());
        }
    }

    private TransacaoModel montarTransacao(TransacaoRequest transacaoRequest) {

        var transacao = new TransacaoModel();

        if (transacaoRequest.tipo() != TipoTransacaoEnum.COMPRA) {
            transacao.setStatus(StatusEmprestimoEnum.EMPRESTADO);
            transacao.setDataDevolucao(transacaoRequest.dataDevolucao());
        }

        transacao.setUsuario(usuarioService.buscarModelPorId(transacaoRequest.idUsuario()));

        var listaLivros = new ArrayList<LivroModel>();
        var valorTransacao = BigDecimal.ZERO;

        for (var livro : transacaoRequest.idLivros()) {
            var livroCadastrado = livroService.buscarPorId(livro);
            livroCadastrado.setDataExclusao(LocalDate.now());
            valorTransacao = valorTransacao.add(aplicarDesconto(livroCadastrado.getPreco(), transacaoRequest.tipo()));
            listaLivros.add(livroCadastrado);
        }

        transacao.setTipo(transacaoRequest.tipo());
        transacao.setDataTransacao(LocalDate.now());
        transacao.setLivros(listaLivros);
        transacao.setValor(valorTransacao);

        return transacao;
    }

    private BigDecimal aplicarDesconto(BigDecimal precoLivro, TipoTransacaoEnum tipo) {
        return tipo == TipoTransacaoEnum.EMPRESTIMO ? precoLivro.subtract(precoLivro.multiply(BigDecimal.valueOf(0.25))) : precoLivro;
    }

    private void validarDatas(LocalDate dataEmprestimo, LocalDate dataDevolucao) {

        if (dataDevolucao.isBefore(dataEmprestimo))
            throw new TransacaoNaoPermitidaException(TransacaoMessage.INVALID_DATE);
    }
}
