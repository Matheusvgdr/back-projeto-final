package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.request.transacao.TransacaoRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.service.LivroService;
import com.projetos.backbibliotecafinal.service.TransacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
@Tag(name = "Transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> salvar(@RequestBody TransacaoRequest transacaoRequest){
        var resultado = transacaoService.salvar(transacaoRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @GetMapping("{idUsuario}")
    public ResponseEntity<ApiResponse<?>> buscarPorUsuario(@PathVariable("idUsuario") Long id){
        var resultado = transacaoService.buscarPorUsuario(id);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado) ;
    }


}
