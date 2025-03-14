package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.request.editora.EditoraRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.entity.EditoraModel;
import com.projetos.backbibliotecafinal.service.EditoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/editoras")
@RequiredArgsConstructor
public class EditoraController {

    private final EditoraService editoraService;


    @PostMapping
    public ResponseEntity<ApiResponse<?>> salvar(@RequestBody EditoraRequest editoraRequest) {
        var resultado = editoraService.salvar(editoraRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EditoraModel>>> buscar(){
        var resultado = editoraService.buscar();
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @PutMapping("/{autorId}")
    public ResponseEntity<ApiResponse<?>> atualizar(@PathVariable(name = "autorId") Long id, @RequestBody EditoraRequest editoraRequest) {
        var resultado = editoraService.atualizar(id, editoraRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @DeleteMapping("/{autorId}")
    public ResponseEntity<ApiResponse<?>> deletar(@PathVariable(name = "autorId") Long id) {
        var resultado = editoraService.deletar(id);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }
}
