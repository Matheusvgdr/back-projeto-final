package com.projetos.backbibliotecafinal.controller;


import com.projetos.backbibliotecafinal.dto.request.AutorRequest;
import com.projetos.backbibliotecafinal.dto.request.BibliotecaRequest;
import com.projetos.backbibliotecafinal.dto.request.LivroRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import com.projetos.backbibliotecafinal.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PatchMapping("/{livroId}")
    public ResponseEntity<ApiResponse<?>> atualizar(@PathVariable(name = "livroId") Long id, @RequestBody LivroRequest livroRequest){
        var resultado =  livroService.atualizar(id, livroRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LivroModel>>> buscarTodos(){
        var resultado = livroService.buscarTodos();
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

}
