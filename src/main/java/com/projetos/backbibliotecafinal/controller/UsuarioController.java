package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.request.usuario.UsuarioRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<ApiResponse<Long>> salvar(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        var resultado = usuarioService.salvar(usuarioRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }


}
