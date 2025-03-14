package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.request.biblioteca.BibliotecaRequest;
import com.projetos.backbibliotecafinal.dto.request.biblioteca.BibliotecaUpdateRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.entity.BibliotecaModel;
import com.projetos.backbibliotecafinal.service.BibliotecaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @PutMapping("/{bibliotecaId}")
    public ResponseEntity<ApiResponse<?>> atualizar(@PathVariable(name = "bibliotecaId") Long id, @RequestBody BibliotecaUpdateRequest bibliotecaRequest) {
        var resultado = bibliotecaService.atualizar(id, bibliotecaRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BibliotecaModel>>> buscar(){
        var resultado = bibliotecaService.buscar();
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @DeleteMapping("/{bibliotecaId}")
    public ResponseEntity<ApiResponse<?>> deletar(@PathVariable(name = "bibliotecaId") Long id) {
        var resultado = bibliotecaService.deletar(id);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }
}