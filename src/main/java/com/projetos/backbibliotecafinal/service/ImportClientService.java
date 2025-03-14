package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.client.ImportClient;
import com.projetos.backbibliotecafinal.constants.messages.ImportacaoMessage;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.ImportacaoReponse;
import com.projetos.backbibliotecafinal.entity.AutorModel;
import com.projetos.backbibliotecafinal.entity.EditoraModel;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportClientService {
    private final AutorService autorService;
    private final EditoraService editoraService;
    private final ImportClient importClient;
    private final LivroService livroService;
    private final BibliotecaService bibliotecaService;

    public ApiResponse<ImportacaoReponse> importarDados(Long id) {
        var dadosImportados = importClient.importarAutores();

        long insercoesLivros = 0;
        long insercoesAutores = 0;
        long insercoesEditoras = 0;


        for (var dado : dadosImportados) {

            var autor = autorService.buscarOuSalvar(new AutorModel(dado.autor()));
            if (!autor.isExiste())
                insercoesAutores++;

            var editora = editoraService.buscarOuSalvar(new EditoraModel(dado.editora()));
            if (!editora.isExiste())
                insercoesEditoras++;

            var livro = livroService.buscarOuCriarLivro(new LivroModel(dado.nomeLivro(), autor.getDado(), editora.getDado(), dado.precoLivro(), dado.isbn(), bibliotecaService.buscarBibliotecaPorUsuarioId(id)));
            if (!livro.isExiste())
                insercoesLivros++;
        }

        return new ApiResponse<>(new ImportacaoReponse(insercoesLivros, insercoesEditoras,insercoesAutores), ImportacaoMessage.DADOS_IMPORTADOS, HttpStatus.CREATED.value());
    }
}
