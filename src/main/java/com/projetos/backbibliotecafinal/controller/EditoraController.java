package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.request.editora.EditoraRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.EditoraResponse;
import com.projetos.backbibliotecafinal.entity.EditoraModel;
import com.projetos.backbibliotecafinal.service.EditoraService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/editora")
@RequiredArgsConstructor
@Tag(name = "Editora")
public class EditoraController {

    private final EditoraService editoraService;


    @PreAuthorize("hasAnyRole('FUNCIONARIO')")
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> salvar(@RequestBody EditoraRequest editoraRequest) {
        var resultado = editoraService.salvar(editoraRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'CLIENTE')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EditoraResponse>>> buscar(){
        var resultado = editoraService.buscar();
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO')")
    @PutMapping("/{autorId}")
    public ResponseEntity<ApiResponse<?>> atualizar(@PathVariable(name = "autorId") Long id, @RequestBody EditoraRequest editoraRequest) {
        var resultado = editoraService.atualizar(id, editoraRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO')")
    @DeleteMapping("/{autorId}")
    public ResponseEntity<ApiResponse<?>> deletar(@PathVariable(name = "autorId") Long id) {
        var resultado = editoraService.deletar(id);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }
}
