package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.constants.enums.StatusEmprestimoEnum;
import com.projetos.backbibliotecafinal.constants.enums.TipoTransacaoEnum;
import com.projetos.backbibliotecafinal.constants.messages.TransacaoMessage;
import com.projetos.backbibliotecafinal.dto.request.transacao.TransacaoRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.TransacaoResponse;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import com.projetos.backbibliotecafinal.entity.TransacaoModel;
import com.projetos.backbibliotecafinal.handler.exceptions.TransacaoNaoPermitidaException;
import com.projetos.backbibliotecafinal.repository.TransacaoRepository;
import com.projetos.backbibliotecafinal.utils.mapper.LivroMapper;
import com.projetos.backbibliotecafinal.utils.mapper.TransacaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

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

        transacaoRepository.findByIdUsuario(transacaoRequest.idUsuario()).ifPresentOrElse(transacaoModel -> {
            throw new TransacaoNaoPermitidaException(TransacaoMessage.EMPRESTIMO_LOCALIZADO);
        }, () -> {
            transacaoRepository.save(montarTransacao(transacaoRequest));
        });

        return new ApiResponse<>(null, TransacaoMessage.SAVE_SUCCESS, HttpStatus.CREATED.value());
    }

    public ApiResponse<TransacaoResponse> buscarPorUsuario(long idUsuario) {
        var transacao = transacaoRepository.findByIdUsuario(idUsuario);

        if (transacao.isEmpty())
            throw new RuntimeException("transacao");

        var response = new TransacaoResponse();

        response.setValor(transacao.get().getValor());
        response.setDataTransacao(transacao.get().getDataTransacao());
        response.setDataDevolucao(transacao.get().getDataDevolucao());
        response.setTipo(transacao.get().getTipo());
        response.setStatus(transacao.get().getStatus());
        response.setLivros(livroMapper.toListLivroResponse(transacao.get().getLivros()));

        return new ApiResponse<>(response, "", HttpStatus.OK.value());
    }

    private TransacaoModel montarTransacao(TransacaoRequest transacaoRequest) {

        var emprestimoNovo = new TransacaoModel();

        if (transacaoRequest.tipo() != TipoTransacaoEnum.COMPRA)
            emprestimoNovo.setStatus(StatusEmprestimoEnum.EMPRESTADO);

        emprestimoNovo.setUsuario(usuarioService.buscarModelPorId(transacaoRequest.idUsuario()));

        var listaLivros = new ArrayList<LivroModel>();
        var valorTransacao = BigDecimal.ZERO;

        for (var livro : transacaoRequest.idLivros()) {
            var livroCadastrado = livroService.buscarPorId(livro);
            valorTransacao = valorTransacao.add(livroCadastrado.getPreco());
            listaLivros.add(livroCadastrado);
        }

        emprestimoNovo.setTipo(transacaoRequest.tipo());
        emprestimoNovo.setDataTransacao(LocalDate.now());
        emprestimoNovo.setLivros(listaLivros);
        emprestimoNovo.setValor(valorTransacao);

        return emprestimoNovo;
    }

    private void validarDatas(LocalDate dataEmprestimo, LocalDate dataDevolucao) {

        if (dataDevolucao.isBefore(dataEmprestimo))
            throw new TransacaoNaoPermitidaException(TransacaoMessage.DATA_INVALIDA);
    }
}
