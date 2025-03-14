package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.autor.AutorRequest;
import com.projetos.backbibliotecafinal.entity.AutorModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    AutorModel toAutorModel(AutorRequest autorRequest);
}
