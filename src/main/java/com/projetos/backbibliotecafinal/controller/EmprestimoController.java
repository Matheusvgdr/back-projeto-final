package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.request.EmprestimoRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> salvar(@RequestBody EmprestimoRequest emprestimoRequest){
        var resultado = emprestimoService.salvar(emprestimoRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }
}
