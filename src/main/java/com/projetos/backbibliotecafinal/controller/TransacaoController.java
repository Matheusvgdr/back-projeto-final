package com.projetos.backbibliotecafinal.controller;

import com.projetos.backbibliotecafinal.constants.enums.StatusEmprestimoEnum;
import com.projetos.backbibliotecafinal.constants.messages.TransacaoMessage;
import com.projetos.backbibliotecafinal.dto.request.TransacaoRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.entity.AutorModel;
import com.projetos.backbibliotecafinal.entity.EditoraModel;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import com.projetos.backbibliotecafinal.service.LivroService;
import com.projetos.backbibliotecafinal.service.TransacaoService;
import com.projetos.backbibliotecafinal.utils.mapper.LivroMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
