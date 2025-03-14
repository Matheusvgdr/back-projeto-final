package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.dto.request.transacao.TransacaoRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.service.LivroService;
import com.projetos.backbibliotecafinal.service.TransacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
@Tag(name = "Transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'CLIENTE')")
    @PostMapping
    public ResponseEntity<ApiResponse<?>> salvar(@RequestBody TransacaoRequest transacaoRequest){
        var resultado = transacaoService.salvar(transacaoRequest);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'CLIENTE')")
    @PatchMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<?>> devolverEmprestimo(@PathVariable("idUsuario") Long id){
        var resultado = transacaoService.devolverLivro(id);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'CLIENTE')")
    @GetMapping("emprestimos/{idUsuario}")
    public ResponseEntity<ApiResponse<?>> buscarEmprestimosPorUsuario(@PathVariable("idUsuario") Long id){
        var resultado = transacaoService.buscarEmprestimosPorUsuario(id);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado) ;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'CLIENTE')")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<?>> buscarTransacoesPorUsuario(@PathVariable("idUsuario") Long id){
        var resultado = transacaoService.buscarTransacoes(id);
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado) ;
    }

}
