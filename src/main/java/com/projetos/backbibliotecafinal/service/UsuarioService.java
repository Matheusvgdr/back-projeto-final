package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.client.ImportClient;
import com.projetos.backbibliotecafinal.constants.enums.TipoUsuarioEnum;
import com.projetos.backbibliotecafinal.constants.messages.UsuarioMessage;
import com.projetos.backbibliotecafinal.dto.request.UsuarioRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.UsuarioResponse;
import com.projetos.backbibliotecafinal.entity.UsuarioModel;
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

    public ApiResponse<?> salvar(UsuarioRequest usuarioRequest) {

        var usuarioNovo = usuarioMapper.toUsuarioModel(usuarioRequest);
        usuarioNovo.setSenha(passwordEncoder.encode(usuarioNovo.getSenha()));

        usuarioRepository.save(usuarioNovo);

        return new ApiResponse<>(null, UsuarioMessage.CADASTRO_SUCESSO, HttpStatus.CREATED.value());
    }

    public ApiResponse<UsuarioResponse> buscarResponsePorId(Long id) {

        var usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty())
            throw new RuntimeException();

        var usuarioResponse = usuarioMapper.toUsuarioResponse(usuario.get());

        return new ApiResponse<>(usuarioResponse, "", HttpStatus.OK.value());
    }


    public UsuarioModel buscarModelPorId(Long id) {
        var usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty())
            throw new RuntimeException();

        return usuario.get();
    }

    public void importarAutores() {
        var teste = autorClient.importarAutores();
    }


}
;