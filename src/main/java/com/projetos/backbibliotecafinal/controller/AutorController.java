package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.request.autor.AutorRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.AutorResponse;
import com.projetos.backbibliotecafinal.entity.AutorModel;
import com.projetos.backbibliotecafinal.service.AutorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autor")
@RequiredArgsConstructor
@Tag(name = "Autor")
public class AutorController {

    private final AutorService autorService;


    @PreAuthorize("hasAnyRole('FUNCIONARIO')")
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> salvar(@RequestBody AutorRequest autorRequest) {
        var resultado = autorService.salvar(autorRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AutorResponse>>> buscar(){
        var resultado = autorService.buscar();
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO')")
    @PutMapping("/{autorId}")
    public ResponseEntity<ApiResponse<?>> atualizar(@PathVariable(name = "autorId") Long id, @RequestBody AutorRequest autorRequest) {
        var resultado = autorService.atualizar(id, autorRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO')")
    @DeleteMapping("/{autorId}")
    public ResponseEntity<ApiResponse<?>> deletar(@PathVariable(name = "autorId") Long id) {
        var resultado = autorService.deletar(id);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }
}
