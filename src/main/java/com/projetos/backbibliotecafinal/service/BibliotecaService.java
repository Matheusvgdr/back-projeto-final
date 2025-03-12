package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.dto.request.BibliotecaRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.repository.BibliotecaRepository;
import com.projetos.backbibliotecafinal.utils.mapper.BibliotecaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final BibliotecaMapper bibliotecaMapper;
    private final UsuarioService usuarioService;

    public ApiResponse<?> salvar(BibliotecaRequest bibliotecaRequest){

        var bibliotecaNova = bibliotecaMapper.toBibliotecaModel(bibliotecaRequest);
        bibliotecaNova.setUsuario(usuarioService.buscarModelPorId(bibliotecaRequest.idUsuario()));
        bibliotecaRepository.save(bibliotecaNova);

        return new ApiResponse<>(null, "", HttpStatus.CREATED.value());
    }

    public void get(){
        var teste = bibliotecaRepository.findAll();
        var po = "21";
    }


}
