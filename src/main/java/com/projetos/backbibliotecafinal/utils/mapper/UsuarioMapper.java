package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.usuario.UsuarioRequest;
import com.projetos.backbibliotecafinal.dto.response.UsuarioResponse;
import com.projetos.backbibliotecafinal.entity.UsuarioModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioModel toUsuarioModel(UsuarioRequest usuarioRequest);
    UsuarioResponse toUsuarioResponse(UsuarioModel usuarioModel);
}
