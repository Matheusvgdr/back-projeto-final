package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.client.ImportClient;
import com.projetos.backbibliotecafinal.constants.messages.UsuarioMessage;
import com.projetos.backbibliotecafinal.dto.request.usuario.UsuarioRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.UsuarioResponse;
import com.projetos.backbibliotecafinal.entity.UsuarioModel;
import com.projetos.backbibliotecafinal.handler.exceptions.UsuarioNaoEncontradoException;
import com.projetos.backbibliotecafinal.repository.UsuarioRepository;
import com.projetos.backbibliotecafinal.utils.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;
    private final ImportClient autorClient;

    public ApiResponse<Long> salvar(UsuarioRequest usuarioRequest) {
        validarFuncionario(2L);
        var usuarioNovo = usuarioMapper.toUsuarioModel(usuarioRequest);
        usuarioNovo.setSenha(passwordEncoder.encode(usuarioNovo.getSenha()));

        var resultado = usuarioRepository.save(usuarioNovo);

        return new ApiResponse<>(resultado.getId(), UsuarioMessage.SAVE_SUCESS.formatted(resultado.getNome()), HttpStatus.CREATED.value());
    }

    public ApiResponse<UsuarioResponse> buscarResponsePorId(Long id) {

        var usuario = usuarioRepository.findByIdActive(id);

        if (usuario.isEmpty())
            throw new UsuarioNaoEncontradoException(UsuarioMessage.REGISTER_NOT_FOUND);

        var usuarioResponse = usuarioMapper.toUsuarioResponse(usuario.get());

        return new ApiResponse<>(usuarioResponse, UsuarioMessage.SEARCH_SUCCESS, HttpStatus.OK.value());
    }

    public UsuarioModel buscarModelPorId(Long id) {
        var usuario = usuarioRepository.findByIdActive(id);

        if (usuario.isEmpty())
            throw new UsuarioNaoEncontradoException(UsuarioMessage.REGISTER_NOT_FOUND);

        return usuario.get();
    }

    public void validarFuncionario(Long id) {
        usuarioRepository.findFuncionarioActive(id);
    }

}
