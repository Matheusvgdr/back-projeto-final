package com.projetos.backbibliotecafinal.controller;


import com.projetos.backbibliotecafinal.dto.request.livro.LivroRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.LivroResponse;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import com.projetos.backbibliotecafinal.service.LivroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
@RequiredArgsConstructor
@Tag(name = "Livro")
public class LivroController {

    private final LivroService livroService;

    @PreAuthorize("hasAnyRole('FUNCIONARIO')")
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> salvar(@RequestBody @Valid LivroRequest livroRequest) {
        var resultado = livroService.salvar(livroRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO')")
    @PatchMapping("/{livroId}")
    public ResponseEntity<ApiResponse<?>> atualizar(@PathVariable(name = "livroId") Long id, @RequestBody LivroRequest livroRequest){
        var resultado =  livroService.atualizar(id, livroRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LivroResponse>>> buscarTodos(){
        var resultado = livroService.buscarTodos();
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO')")
    @DeleteMapping("/{livroId}")
    public ResponseEntity<ApiResponse<?>> deletar(@PathVariable(name = "livroId") Long id) {
        var resultado = livroService.deletar(id);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

}
