package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.constants.messages.LivroMessage;
import com.projetos.backbibliotecafinal.dto.request.livro.LivroRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.InsercaoExistenteResponse;
import com.projetos.backbibliotecafinal.dto.response.LivroResponse;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import com.projetos.backbibliotecafinal.handler.exceptions.AutorNaoEncontradoException;
import com.projetos.backbibliotecafinal.handler.exceptions.LivroNaoEncontradoException;
import com.projetos.backbibliotecafinal.repository.LivroRepository;
import com.projetos.backbibliotecafinal.utils.mapper.LivroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;
    private final AutorService autorService;
    private final EditoraService editoraService;
    private final BibliotecaService bibliotecaService;

    public ApiResponse<List<LivroResponse>> buscarTodos(){
        var livros = livroRepository.findAllActive();

        if(livros.isEmpty())
            throw new LivroNaoEncontradoException(LivroMessage.SEARCH_LIST_NOT_FOUND);

        return new ApiResponse<>(livroMapper.toListLivroResponse(livros), LivroMessage.SEARCH_LIST_SUCCESS, HttpStatus.OK.value());
    }

    public ApiResponse<?> salvar(LivroRequest livroRequest) {

        var livroNovo = livroMapper.toLivroModel(livroRequest);

        livroNovo.setAutor(autorService.buscarPorId(livroRequest.idAutor()));
        livroNovo.setEditora(editoraService.buscarPorId(livroRequest.idEditora()));
        livroNovo.setBiblioteca(bibliotecaService.buscarPorId(livroRequest.idBiblioteca()));
        livroRepository.save(livroNovo);

        return new ApiResponse<>(null, LivroMessage.SAVE_SUCCESS, HttpStatus.CREATED.value());
    }

    public ApiResponse<?> atualizar(Long idLivro, LivroRequest livroRequest) {
        var livroOriginal = livroRepository.findByIdActive(idLivro);

        if (livroOriginal.isEmpty())
            throw new LivroNaoEncontradoException(LivroMessage.REGISTER_NOT_FOUND);

        var livroNovo = livroOriginal.get();

        if (livroRequest.titulo() != null) {
            livroNovo.setTitulo(livroRequest.titulo());
        }

        if (livroRequest.isbn() != null) {
            livroNovo.setIsbn(livroRequest.isbn());
        }

        if (livroRequest.preco() != null) {
            livroNovo.setPreco(livroRequest.preco());
        }
        if (livroRequest.idEditora() != null) {
            livroNovo.setEditora(editoraService.buscarPorId(livroRequest.idEditora()));
        }

        if (livroRequest.idAutor() != null) {
            livroNovo.setAutor(autorService.buscarPorId(livroRequest.idAutor()));
        }

        livroRepository.save(livroNovo);

        return new ApiResponse<>(null, LivroMessage.UPDATE_SUCCESS, HttpStatus.OK.value());
    }

    public ApiResponse<?> deletar(Long id) {
        livroRepository.findByIdActive(id).ifPresent(livro -> {
            livro.setDataExclusao(LocalDate.now());
            livroRepository.save(livro);
        });

        return new ApiResponse<>(null, LivroMessage.DELETE_SUCCESS.formatted(), HttpStatus.OK.value());
    }

    public LivroModel buscarPorId(Long id){
        return livroRepository.findByIdActive(id).orElseThrow(() -> new LivroNaoEncontradoException(LivroMessage.REGISTER_NOT_FOUND));
    }

    public InsercaoExistenteResponse<LivroModel> buscarOuCriarLivro(LivroModel livroModel) {
        var resultado = new InsercaoExistenteResponse<LivroModel>();
        var livro = livroRepository.findByIsbn(livroModel.getIsbn());

        if (livro.isPresent()) {
            resultado.setDado(livro.get());
            resultado.setExiste(true);
        } else {
            resultado.setExiste(false);
            resultado.setDado(livroRepository.save(livroModel));
        }
        return resultado;
    }

}
