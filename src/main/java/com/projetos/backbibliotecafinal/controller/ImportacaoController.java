package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.ImportacaoReponse;
import com.projetos.backbibliotecafinal.service.ImportClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/importacao")
@RequiredArgsConstructor
@Tag(name = "Importação")
public class ImportacaoController {

    private final ImportClientService importClientService;

    @PostMapping
    public ResponseEntity<ApiResponse<ImportacaoReponse>> salvar() {
        var resultado = importClientService.importarDados();
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }
}
