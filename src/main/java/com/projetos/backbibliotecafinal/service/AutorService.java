package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.constants.messages.AutorMessage;
import com.projetos.backbibliotecafinal.dto.request.autor.AutorRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.AutorResponse;
import com.projetos.backbibliotecafinal.dto.response.InsercaoExistenteResponse;
import com.projetos.backbibliotecafinal.entity.AutorModel;
import com.projetos.backbibliotecafinal.handler.exceptions.AutorNaoEncontradoException;
import com.projetos.backbibliotecafinal.repository.AutorRepository;
import com.projetos.backbibliotecafinal.utils.mapper.AutorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    public ApiResponse<Long> salvar(AutorRequest autorRequest) {
        var resultado = autorRepository.save(autorMapper.toAutorModel(autorRequest));

        return new ApiResponse<>(resultado.getId(), AutorMessage.SAVE_SUCCESS, HttpStatus.CREATED.value());
    }

    public ApiResponse<List<AutorResponse>> buscar() {
        var autores = autorRepository.findAllActive();

        if (autores.isEmpty()) {
            throw new AutorNaoEncontradoException(AutorMessage.SEARCH_LIST_NOT_FOUND);
        }

        return new ApiResponse<>(autorMapper.toAutorResponseList(autores), AutorMessage.SEARCH_LIST_SUCCESS, HttpStatus.OK.value());
    }

    public AutorModel buscarPorId(Long id) {
        return autorRepository.findByIdActive(id).orElseThrow(() -> new AutorNaoEncontradoException(AutorMessage.REGISTER_NOT_FOUND));
    }

    public InsercaoExistenteResponse<AutorModel> buscarOuSalvar(AutorModel autorModel) {
        var resultado = new InsercaoExistenteResponse<AutorModel>();
        var autor = autorRepository.findByNome((autorModel.getNome()));

        if (autor.isPresent()) {
            resultado.setDado(autor.get());
            resultado.setExiste(true);
        } else {
            resultado.setExiste(false);
            resultado.setDado(autorRepository.save(autorModel));
        }
        return resultado;

        //return autorRepository.findByNome((autorModel.getNome())).orElseGet(() -> autorRepository.save(autorModel));
    }

    public ApiResponse<?> atualizar(Long id, AutorRequest autorRequest) {
        var autorOriginal = autorRepository.findById(id);

        autorOriginal.ifPresentOrElse(autor -> {
            autor.setNome(autorRequest.nome());
            autorRepository.save(autor);
        }, () -> {
            throw new AutorNaoEncontradoException(AutorMessage.REGISTER_NOT_FOUND);
        });

        return new ApiResponse<>(null, AutorMessage.UPDATE_SUCCESS, HttpStatus.OK.value());
    }

    public ApiResponse<?> deletar(Long id) {
        autorRepository.findById(id).ifPresentOrElse(autor -> {
            autor.setDataExclusao(LocalDate.now());
            autorRepository.save(autor);
        }, () -> {
            throw new AutorNaoEncontradoException(AutorMessage.REGISTER_NOT_FOUND);
        });

        return new ApiResponse<>(null, AutorMessage.DELETE_SUCCESS, HttpStatus.OK.value());
    }

}
