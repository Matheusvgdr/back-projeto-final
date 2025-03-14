package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.constants.messages.BibliotecaMessage;
import com.projetos.backbibliotecafinal.constants.messages.EditoraMessage;
import com.projetos.backbibliotecafinal.dto.request.editora.EditoraRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.EditoraResponse;
import com.projetos.backbibliotecafinal.dto.response.InsercaoExistenteResponse;
import com.projetos.backbibliotecafinal.entity.AutorModel;
import com.projetos.backbibliotecafinal.entity.EditoraModel;
import com.projetos.backbibliotecafinal.handler.exceptions.BibliotecaNaoEncontradaException;
import com.projetos.backbibliotecafinal.handler.exceptions.EditoraNaoEncontradaException;
import com.projetos.backbibliotecafinal.repository.EditoraRepository;
import com.projetos.backbibliotecafinal.utils.mapper.EditoraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EditoraService {

    private final EditoraRepository editoraRepository;
    private final EditoraMapper editoraMapper;

    public ApiResponse<Long> salvar(EditoraRequest editoraRequest) {

        var novaEditora = editoraMapper.toEditoraModel(editoraRequest);
        var resultado = editoraRepository.save(novaEditora);

        return new ApiResponse<>(resultado.getId(), EditoraMessage.SAVE_SUCCESS, HttpStatus.CREATED.value());
    }

    public InsercaoExistenteResponse<EditoraModel> buscarOuSalvar(EditoraModel editoraModel) {
        var resultado = new InsercaoExistenteResponse<EditoraModel>();

        var editora = editoraRepository.findByNome(editoraModel.getNome());

        editora.ifPresentOrElse(registro -> {
            resultado.setDado(editora.get());
            resultado.setExiste(true);
        }, () -> {
            resultado.setExiste(false);
            resultado.setDado(editoraRepository.save(editoraModel));
        });

        return resultado;
        //return editoraRepository.findByNome(editoraModel.getNome()).isPresent() ? null : editoraRepository.save(editoraModel);
    }

    public ApiResponse<List<EditoraResponse>> buscar() {
        var resultado = editoraRepository.findAllActive();

        if (resultado.isEmpty()) {
            throw new EditoraNaoEncontradaException(EditoraMessage.REGISTER_NOT_FOUND);
        }

        return new ApiResponse<>(editoraMapper.toEditoraResponseList(resultado), EditoraMessage.SEARCH_LIST_SUCCESS, HttpStatus.OK.value());
    }

    public EditoraModel buscarPorId(Long id) {
        return editoraRepository.findByIdActive(id).orElseThrow(() -> new EditoraNaoEncontradaException(EditoraMessage.REGISTER_NOT_FOUND));
    }

    public ApiResponse<?> atualizar( Long idEditora, EditoraRequest editoraRequest) {

        var editoraOriginal = editoraRepository.findById(idEditora);

        editoraOriginal.ifPresentOrElse(autor -> {
            autor.setNome(editoraRequest.nome());
            editoraRepository.save(autor);
        }, () -> {
            throw new EditoraNaoEncontradaException(EditoraMessage.REGISTER_NOT_FOUND);
        });

        return new ApiResponse<>(null, EditoraMessage.UPDATE_SUCCESS, HttpStatus.OK.value());
    }

    public ApiResponse<?> deletar(Long id) {
        editoraRepository.findByIdActive(id).ifPresentOrElse(editora -> {
            editora.setDataExclusao(LocalDate.now());
            editoraRepository.save(editora);
        }, () -> {
            throw new EditoraNaoEncontradaException(EditoraMessage.REGISTER_NOT_FOUND);
        });

        return new ApiResponse<>(null, EditoraMessage.DELETE_SUCCESS, HttpStatus.OK.value());
    }


}
