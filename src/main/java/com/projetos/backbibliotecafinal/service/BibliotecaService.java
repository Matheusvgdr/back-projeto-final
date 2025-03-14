package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.constants.messages.BibliotecaMessage;
import com.projetos.backbibliotecafinal.dto.request.biblioteca.BibliotecaRequest;
import com.projetos.backbibliotecafinal.dto.request.biblioteca.BibliotecaUpdateRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.entity.BibliotecaModel;
import com.projetos.backbibliotecafinal.handler.exceptions.BibliotecaNaoEncontradaException;
import com.projetos.backbibliotecafinal.repository.BibliotecaRepository;
import com.projetos.backbibliotecafinal.utils.mapper.BibliotecaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final BibliotecaMapper bibliotecaMapper;
    private final UsuarioService usuarioService;

    public ApiResponse<?> salvar(BibliotecaRequest bibliotecaRequest){

        var bibliotecaNova = bibliotecaMapper.toBibliotecaModel(bibliotecaRequest);
        bibliotecaNova.setUsuario(usuarioService.buscarModelPorId(bibliotecaRequest.idUsuario()));
        bibliotecaRepository.save(bibliotecaNova);

        return new ApiResponse<>(null, BibliotecaMessage.SAVE_SUCCESS, HttpStatus.CREATED.value());
    }

    public ApiResponse<List<BibliotecaModel>> buscar() { //TODO: criar response retirando a data de exclusao
        var bibliotecas = bibliotecaRepository.findAllActive();

        if (bibliotecas.isEmpty()) {
            throw new BibliotecaNaoEncontradaException(BibliotecaMessage.SEARCH_LIST_NOT_FOUND);
        }

        return new ApiResponse<>(bibliotecas, BibliotecaMessage.SEARCH_LIST_SUCCESS, HttpStatus.OK.value());
    }

    public ApiResponse<?> atualizar(Long id, BibliotecaUpdateRequest bibliotecaRequest) {
        var bibliotecaOriginal = bibliotecaRepository.findByIdActive(id);

        bibliotecaOriginal.ifPresentOrElse(biblioteca -> {
            if(bibliotecaRequest.nome() != null)
                biblioteca.setNome(bibliotecaRequest.nome());

            if(bibliotecaRequest.endereco() != null)
                biblioteca.setEndereco(bibliotecaRequest.endereco());

            bibliotecaRepository.save(biblioteca);
        }, () -> {
            throw new BibliotecaNaoEncontradaException(BibliotecaMessage.REGISTER_NOT_FOUND);
        });

        return new ApiResponse<>(null, BibliotecaMessage.UPDATE_SUCCESS, HttpStatus.OK.value());
    }

    public ApiResponse<?> deletar(Long id) {
        bibliotecaRepository.findByIdActive(id).ifPresentOrElse(biblioteca -> {
            biblioteca.setDataExclusao(LocalDate.now());
            bibliotecaRepository.save(biblioteca);
        }, () -> {
            throw new BibliotecaNaoEncontradaException(BibliotecaMessage.REGISTER_NOT_FOUND);
        });

        return new ApiResponse<>(null, BibliotecaMessage.DELETE_SUCCESS, HttpStatus.OK.value());
    }

    public BibliotecaModel buscarBibliotecaPorUsuarioId(Long idUsuario) {
        var biblioteca = bibliotecaRepository.findByUsuario(idUsuario);

        if (biblioteca.isEmpty())
            throw new BibliotecaNaoEncontradaException(BibliotecaMessage.REGISTER_NOT_FOUND);

        return biblioteca.get();
    }

    public BibliotecaModel buscarPorId(Long id) {
        return bibliotecaRepository.findByIdActive(id).orElseThrow(() -> new BibliotecaNaoEncontradaException(BibliotecaMessage.REGISTER_NOT_FOUND));
    }




}
