package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.request.BibliotecaRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.service.BibliotecaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/biblioteca")
@RequiredArgsConstructor
@Tag(name = "Biblioteca")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> salvar(@RequestBody @Valid BibliotecaRequest bibliotecaRequest) {
        var resultado = bibliotecaService.salvar(bibliotecaRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @GetMapping
    public void teste() {
        
    }
}