package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.constants.messages.AutorMessage;
import com.projetos.backbibliotecafinal.constants.messages.EditoraMessage;
import com.projetos.backbibliotecafinal.dto.request.EditoraRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.InsercaoExistenteResponse;
import com.projetos.backbibliotecafinal.entity.EditoraModel;
import com.projetos.backbibliotecafinal.handler.exceptions.AutorNaoEncontradoException;
import com.projetos.backbibliotecafinal.handler.exceptions.EditoraNaoEncontradaException;
import com.projetos.backbibliotecafinal.repository.EditoraRepository;
import com.projetos.backbibliotecafinal.utils.mapper.EditoraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditoraService {

    private final EditoraRepository editoraRepository;
    private final EditoraMapper editoraMapper;

    public ApiResponse<?> salvar(EditoraRequest editoraRequest) {

        var novaEditora = editoraMapper.toEditoraModel(editoraRequest);
        editoraRepository.save(novaEditora);

        return new ApiResponse<>(null, EditoraMessage.SAVE_SUCCESS, HttpStatus.CREATED.value());
    }

    public InsercaoExistenteResponse<EditoraModel> buscarOuSalvar(EditoraModel editoraModel) {
        var resultado = new InsercaoExistenteResponse<EditoraModel>();

        var editora = editoraRepository.findByNome(editoraModel.getNome());

        if (editora.isPresent()) {
            resultado.setDado(editora.get());
            resultado.setExiste(true);
        } else {
            resultado.setExiste(false);
            resultado.setDado(editoraRepository.save(editoraModel));
        }

        return resultado;
        //return editoraRepository.findByNome(editoraModel.getNome()).isPresent() ? null : editoraRepository.save(editoraModel);
    }

    public ApiResponse<?> buscarTodas() {
        var resultado = editoraRepository.findAll();

        if(resultado.isEmpty()){
            return new ApiResponse<>(null, EditoraMessage.SEARCH_LIST_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }

        return new ApiResponse<>(resultado, EditoraMessage.SEARCH_LIST_SUCCESS, HttpStatus.OK.value());
    }

    public EditoraModel buscarPorId(Long id) {
        return editoraRepository.findByIdActive(id).orElseThrow(() -> new EditoraNaoEncontradaException(EditoraMessage.INDIVIDUAL_NOT_FOUND));
    }

    public ApiResponse<?> atualizar(EditoraRequest editoraRequest, Long idEditora) {

        var editoraOriginal = editoraRepository.findById(idEditora);

        editoraOriginal.ifPresentOrElse(autor -> {
            autor.setNome(editoraRequest.nome());
            editoraRepository.save(autor);
        }, () -> {
            throw new EditoraNaoEncontradaException(EditoraMessage.INDIVIDUAL_NOT_FOUND);
        });

        return new ApiResponse<>(null, EditoraMessage.UPDATE_SUCCESS, HttpStatus.NO_CONTENT.value());
    }



}
