package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.request.UsuarioRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<ApiResponse<?>> salvar(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        var resultado = usuarioService.salvar(usuarioRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @GetMapping
    public void teste(){
        usuarioService.importarAutores();
        var po = 1;
    }

}
