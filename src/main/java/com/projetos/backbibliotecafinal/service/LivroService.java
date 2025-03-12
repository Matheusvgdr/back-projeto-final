package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.constants.messages.LivroMessage;
import com.projetos.backbibliotecafinal.dto.request.LivroRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.InsercaoExistenteResponse;
import com.projetos.backbibliotecafinal.entity.AutorModel;
import com.projetos.backbibliotecafinal.entity.EditoraModel;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import com.projetos.backbibliotecafinal.repository.LivroRepository;
import com.projetos.backbibliotecafinal.utils.mapper.LivroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;
    private final AutorService autorService;
    private final EditoraService editoraService;


    public ApiResponse<?> salvar(LivroRequest livroRequest) {

        var livroNovo = livroMapper.toLivroModel(livroRequest);

        livroNovo.setAutor(autorService.buscarPorId(livroRequest.idAutor()));
        livroNovo.setEditora(editoraService.buscarPorId(livroRequest.idEditora()));

        var teste = livroRepository.save(livroNovo);

        return new ApiResponse<>(null, LivroMessage.CADASTRO_SUCESSO, HttpStatus.CREATED.value());
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
