package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.constants.messages.EmprestimoMessage;
import com.projetos.backbibliotecafinal.dto.request.EmprestimoRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import com.projetos.backbibliotecafinal.handler.exceptions.EmprestimoNaoPermitidoException;
import com.projetos.backbibliotecafinal.repository.EmprestimoRepository;
import com.projetos.backbibliotecafinal.utils.mapper.EmprestimoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final EmprestimoMapper emprestimoMapper;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    public ApiResponse<?> salvar(EmprestimoRequest emprestimoRequest) {

        emprestimoRepository.findByIdUsuario(emprestimoRequest.idUsuario()).ifPresentOrElse(emprestimoModel -> {
            throw new EmprestimoNaoPermitidoException(EmprestimoMessage.EMPRESTIMO_LOCALIZADO);
        }, () -> {
            var emprestimoNovo = emprestimoMapper.toEmprestimoModel(emprestimoRequest);

            emprestimoNovo.setDataEmprestimo(LocalDate.now());
            validarDatas(emprestimoNovo.getDataEmprestimo(), emprestimoNovo.getDataDevolucao());

            emprestimoNovo.setUsuario(usuarioService.buscarModelPorId(emprestimoNovo.getId()));

            var listaLivros = new ArrayList<LivroModel>();

            for (var livro : emprestimoRequest.idLivros()) {
                listaLivros.add(livroService.buscarPorId(livro));
            }

            emprestimoNovo.setLivros(listaLivros);

            emprestimoRepository.save(emprestimoNovo);
        });

        return new ApiResponse<>(null, EmprestimoMessage.SAVE_SUCCESS, HttpStatus.CREATED.value());
    }

    private void validarDatas(LocalDate dataEmprestimo, LocalDate dataDevolucao) {

        if (dataDevolucao.isBefore(dataEmprestimo))
            throw new EmprestimoNaoPermitidoException(EmprestimoMessage.DATA_INVALIDA);
    }
}
