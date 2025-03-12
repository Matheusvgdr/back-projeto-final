package com.projetos.backbibliotecafinal.controller;


import com.projetos.backbibliotecafinal.dto.request.BibliotecaRequest;
import com.projetos.backbibliotecafinal.dto.request.LivroRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livro")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> salvar(@RequestBody @Valid LivroRequest livroRequest) {
        var resultado = livroService.salvar(livroRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

}
